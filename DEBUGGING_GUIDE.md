# Debugging Guide for Thumbnail Display Issues

## Quick Diagnostics Checklist

### 1. Check if Thumbnails are Loading
Run this in Android Studio terminal:
```bash
adb logcat | grep -E "FolderScreen|AlbumGridItem|GlideImage"
```

Expected output when working:
```
FolderScreen: After refresh - albumToThumbnailMapping size: 3
FolderScreen: Album Screenshots (id=1): thumbnail=/storage/emulated/0/Pictures/PixelFlow/Screenshots/image1.jpg
AlbumGridItem: Rendering album: Screenshots, itemId: 123, filePath: ..., thumbnailPath: /storage/...
GlideImage: Loading image from file: /storage/..., exists: true
```

### 2. Verify Folder Structure
The app expects this structure:
```
/storage/emulated/0/Pictures/PixelFlow/
├── Unsorted/
├── Screenshots/
├── Downloads/
└── ... (other folders)
```

Check with:
```bash
adb shell ls -la /storage/emulated/0/Pictures/PixelFlow/
```

### 3. Verify Screenshot Data Exists
Check if screenshots are being saved to SharedPreferences:
```bash
adb shell dumpsys activity com.aks_labs.pixelflow | grep -i screenshot
```

Or use Android Studio's "Database Inspector":
1. View → Tool Windows → Database Inspector
2. Look for the SharedPreferences database
3. Check KEY_SCREENSHOTS and KEY_FOLDERS entries

### 4. Verify File Paths in Database
To see actual stored data:
```bash
adb shell cat /data/data/com.aks_labs.pixelflow/shared_prefs/*.xml
```

Look for lines with:
- `filePath="/storage/..."`
- `thumbnailPath="/storage/..."`
- `folderId="..."`

### 5. Check File Permissions
Ensure the app has:
```bash
adb shell dumpsys package com.aks_labs.pixelflow | grep -i "android.permission"
```

Required:
- `android.permission.READ_EXTERNAL_STORAGE`
- `android.permission.MANAGE_EXTERNAL_STORAGE` (Android 11+)

Grant manually if needed:
```bash
adb shell pm grant com.aks_labs.pixelflow android.permission.READ_EXTERNAL_STORAGE
adb shell pm grant com.aks_labs.pixelflow android.permission.MANAGE_EXTERNAL_STORAGE
```

## Common Issues and Solutions

### Issue 1: "albumToThumbnailMapping size: 0"

**Symptoms:**
```
FolderScreen: After refresh - albumToThumbnailMapping size: 0
```

**Causes:**
- No screenshots in the database
- Screenshots don't have correct `folderId` values
- getScreenshotsByFolder() returning empty list

**Solution:**
1. Check if screenshots exist:
   ```kotlin
   Log.d("DEBUG", "All screenshots: ${getSharedPrefsManager().screenshotsValue}")
   ```
2. Verify folderId values match:
   ```kotlin
   Log.d("DEBUG", "Folders: ${getSharedPrefsManager().foldersValue}")
   ```
3. Check folder IDs:
   ```kotlin
   listOfDirs.forEach { Log.d("DEBUG", "Folder: ${it.name}, ID: ${it.id}") }
   ```

### Issue 2: "thumbnail=null"

**Symptoms:**
```
FolderScreen: Album Screenshots (id=1): thumbnail=null
```

**Causes:**
- Folder exists but has no screenshots
- Screenshots have wrong folderId
- getScreenshotsByFolder() is filtering incorrectly

**Solution:**
1. Manually check:
   ```kotlin
   val allScreenshots = getSharedPrefsManager().screenshotsValue
   val folderId = 1L
   val matching = allScreenshots.filter { it.folderId == folderId }
   Log.d("DEBUG", "Screenshots for folder $folderId: $matching")
   ```
2. Add a test screenshot to the folder manually

### Issue 3: "exists: false"

**Symptoms:**
```
GlideImage: Loading image from file: /storage/.../image.jpg, exists: false
```

**Causes:**
- File path is incorrect
- File was deleted
- File path wasn't properly saved
- Storage permission issue

**Solution:**
1. Verify file exists:
   ```bash
   adb shell test -f "/storage/emulated/0/Pictures/PixelFlow/Screenshots/image.jpg" && echo "EXISTS" || echo "NOT FOUND"
   ```
2. List all image files:
   ```bash
   adb shell find /storage/emulated/0/Pictures/PixelFlow -type f -name "*.jpg" -o -name "*.png"
   ```
3. Check file permissions:
   ```bash
   adb shell ls -la /storage/emulated/0/Pictures/PixelFlow/Screenshots/image.jpg
   ```

### Issue 4: Blank Gray Boxes Instead of Images

**Symptoms:**
- Folder cards appear but with no images
- Shimmer loading effect visible

**Causes:**
- Item.id is 0 (dummy screenshot being used)
- Thumbnail path is empty
- File exists but Coil can't load it

**Solution:**
1. Check what's being rendered:
   ```
   AlbumGridItem: Rendering album: ..., itemId: 0, filePath: "", thumbnailPath: ""
   ```
   If `itemId: 0`, the dummy is being used (thumbnails not loaded)

2. Ensure thumbnails loaded before render:
   - Check `withContext(Dispatchers.IO)` is used
   - Verify `refreshAlbumsAsync()` completes

## Advanced Debugging

### 1. Trace Execution Flow
Add this to FolderScreen LaunchedEffect:

```kotlin
LaunchedEffect(...) {
    Log.d("DEBUG", "LaunchedEffect started")
    
    withContext(Dispatchers.IO) {
        Log.d("DEBUG", "Before refreshAlbumsAsync")
        mainViewModel.refreshAlbumsAsync(listOfDirs)
        Log.d("DEBUG", "After refreshAlbumsAsync, map size: ${mainViewModel.albumsThumbnailsMap.size}")
        
        listOfDirs.forEach { album ->
            Log.d("DEBUG", "Folder ${album.id}: ${mainViewModel.albumsThumbnailsMap[album.id]}")
        }
        
        // ... rest of code ...
    }
    
    Log.d("DEBUG", "LaunchedEffect completed")
}
```

### 2. Monitor Map Updates
In MainViewModel.refreshAlbumsAsync():

```kotlin
suspend fun refreshAlbumsAsync(albums: List<SimpleFolder>) {
    Log.d("DEBUG", "refreshAlbumsAsync started, albums count: ${albums.size}")
    
    albums.forEach { folder ->
        Log.d("DEBUG", "Processing folder: ${folder.name} (id: ${folder.id})")
        
        val thumbs = getSharedPrefsManager().getScreenshotsByFolder(folder.id)
        Log.d("DEBUG", "Found ${thumbs.size} screenshots for folder ${folder.id}")
        
        val sorted = thumbs.sortedByDescending { it.savedTimestamp }
        Log.d("DEBUG", "Sorted screenshots: ${sorted.map { it.filePath }}")
        
        val first = sorted.take(1)
        if (first.isNotEmpty()) {
            _albumsThumbnailsMap[folder.id] = first.first()
            Log.d("DEBUG", "Stored thumbnail for folder ${folder.id}: ${first.first().filePath}")
        } else {
            _albumsThumbnailsMap.remove(folder.id)
            Log.d("DEBUG", "No thumbnails for folder ${folder.id}")
        }
    }
    
    Log.d("DEBUG", "refreshAlbumsAsync completed, final map size: ${_albumsThumbnailsMap.size}")
}
```

### 3. Check Folder Details
Get detailed info about a specific folder:

```kotlin
val folders = mainViewModel.settings.AlbumsList.getNormalAlbums().collectAsState(initial = emptyList()).value
folders.forEach { folder ->
    Log.d("DEBUG", """
        Folder: ${folder.name}
        ID: ${folder.id}
        IsDefault: ${folder.isDefault}
        IsCustom: ${folder.isCustomAlbum}
        IsPinned: ${folder.isPinned}
        Screenshots: ${mainViewModel.getScreenshotsForFolder(folder.id).size}
    """.trimIndent())
}
```

## Testing Checklist

Before deploying to production:

- [ ] Thumbnails appear for all folders with screenshots
- [ ] Correct image is shown (most recent screenshot)
- [ ] No blank gray boxes
- [ ] Images load without errors
- [ ] Logcat shows successful loading:
  ```
  AlbumGridItem: Rendering album: Screenshots, itemId: 123
  GlideImage: Loading image from file: /storage/..., exists: true
  ```
- [ ] Works after app restart
- [ ] Works with multiple folders
- [ ] Works after adding new screenshots
- [ ] Works after deleting screenshots
- [ ] No memory leaks (checked with Profiler)

## Performance Optimization Notes

If there are performance issues with many thumbnails:

1. **Reduce Thumbnail Loading**
   - Only load visible thumbnails (implement lazy loading)
   - Cache thumbnails in memory

2. **Optimize Image Loading**
   - Use smaller thumbnail files
   - Implement disk cache in Coil:
   ```kotlin
   ImageRequest.Builder(context)
       .data(file)
       .diskCachePolicy(CachePolicy.ENABLED)
       .build()
   ```

3. **Batch Operations**
   - Load thumbnails in batches instead of all at once
   - Use coroutine channels or Flow for better control

## Emergency Commands

If something goes wrong, use these to reset:

```bash
# Clear app cache and data
adb shell pm clear com.aks_labs.pixelflow

# Reinstall app
adb uninstall com.aks_labs.pixelflow
adb install app-debug.apk

# Check recent logcat
adb logcat -b all --since='1 hour ago'

# Real-time filtering
adb logcat "*:S" "PixelFlow:D" "FolderScreen:D" "AlbumGridItem:D" "GlideImage:D"
```

## Contact / Support

For detailed debugging, provide:
1. Logcat output with timestamps
2. Number of folders and screenshots
3. File system structure (adb shell ls output)
4. Device Android version
5. App version
