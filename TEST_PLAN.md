# Test Plan - Thumbnail Display Feature

## Test Objective
Verify that FolderScreen correctly displays thumbnail images for each folder.

## Pre-requisites
- App successfully builds without errors
- Device has Android 10+
- Test device has sufficient storage (~500MB)
- App has READ_EXTERNAL_STORAGE and MANAGE_EXTERNAL_STORAGE permissions

## Test Scenarios

### Scenario 1: Basic Thumbnail Display
**Objective**: Verify thumbnails appear for folders with screenshots

**Steps**:
1. Open FolderScreen
2. Verify folders appear in grid
3. Each folder card should display:
   - Folder icon/image
   - Folder name
   - Lock icon (if custom folder)

**Expected Result**: 
- ✅ All folders with screenshots show thumbnail images
- ✅ Images are not blank gray boxes
- ✅ Images match the folder content

**Logcat Verification**:
```
✅ FolderScreen: After refresh - albumToThumbnailMapping size: N (where N > 0)
✅ AlbumGridItem: Rendering album: Screenshots, itemId: 123, ...
✅ GlideImage: Loading image from file: .../image.jpg, exists: true
```

---

### Scenario 2: Empty Folders
**Objective**: Verify behavior for folders with no screenshots

**Steps**:
1. Create a new folder with no screenshots
2. Open FolderScreen
3. Look for the new folder

**Expected Result**:
- ✅ Empty folder appears in grid
- ✅ Shows placeholder/shimmer effect
- ✅ No crash or errors

**Logcat Verification**:
```
✅ FolderScreen: Album NewFolder (id=5): thumbnail=null
(Should show null, not crash)
```

---

### Scenario 3: Multiple Folders
**Objective**: Verify correct thumbnails for multiple folders

**Setup**:
- Have 3+ folders with different screenshots

**Steps**:
1. Open FolderScreen
2. Verify each folder shows DIFFERENT image (most recent)
3. Check that folders are correctly identified

**Expected Result**:
- ✅ Each folder shows its unique most recent screenshot
- ✅ No mixing of images between folders
- ✅ Correct mapping (folder name matches folder ID)

**Logcat Verification**:
```
✅ FolderScreen: Album Screenshots (id=1): thumbnail=/storage/.../shot1.jpg
✅ FolderScreen: Album Downloads (id=2): thumbnail=/storage/.../file1.jpg
✅ FolderScreen: Album Documents (id=3): thumbnail=/storage/.../doc1.jpg
```

---

### Scenario 4: Image Loading Performance
**Objective**: Verify thumbnails load efficiently

**Steps**:
1. Open FolderScreen with 5+ folders
2. Observe loading time and smoothness
3. Scroll through folder grid

**Expected Result**:
- ✅ Thumbnails load within 2 seconds
- ✅ No visible lag when scrolling
- ✅ Grid remains responsive
- ✅ No memory warnings in logcat

**Performance Metrics**:
- Load time: < 2000ms
- Frame rate: > 50 fps
- Memory: < 100MB additional

---

### Scenario 5: Add New Screenshot
**Objective**: Verify thumbnail updates when new screenshot is added

**Steps**:
1. Open FolderScreen (note current thumbnail)
2. Take a new screenshot in a folder
3. Return to FolderScreen
4. Verify thumbnail updated

**Expected Result**:
- ✅ New screenshot appears as thumbnail
- ✅ Old screenshot no longer shown
- ✅ Timestamp matches new screenshot

**Logcat Verification**:
```
Before: AlbumGridItem: Rendering album: Screenshots, itemId: 123
After:  AlbumGridItem: Rendering album: Screenshots, itemId: 456
(ID changed = new screenshot)
```

---

### Scenario 6: Delete Screenshot
**Objective**: Verify thumbnail remains valid after deletion

**Steps**:
1. Delete the current thumbnail screenshot
2. Return to FolderScreen
3. Folder should show next most recent

**Expected Result**:
- ✅ Thumbnail changes to next recent screenshot
- ✅ No crash
- ✅ Folder still displays image

---

### Scenario 7: Landscape Orientation
**Objective**: Verify thumbnails display correctly in landscape

**Steps**:
1. Open FolderScreen
2. Rotate device to landscape
3. Verify thumbnails still visible

**Expected Result**:
- ✅ Grid adapts to landscape (more columns)
- ✅ Thumbnails display correctly
- ✅ No overlapping or clipping

---

### Scenario 8: App Restart
**Objective**: Verify thumbnails persist after app restart

**Steps**:
1. Open FolderScreen, note thumbnails
2. Close app completely
3. Reopen app and go to FolderScreen
4. Verify same thumbnails appear

**Expected Result**:
- ✅ Thumbnails are same before and after restart
- ✅ Data persists correctly
- ✅ No data loss

---

### Scenario 9: Storage Permission Denied
**Objective**: Verify app behavior without storage permissions

**Steps**:
1. Revoke READ_EXTERNAL_STORAGE permission
2. Open FolderScreen
3. Observe behavior

**Expected Result**:
- ✅ App shows permission request
- ✅ No crash
- ✅ Graceful handling of permission denial

---

### Scenario 10: Folder Sorting
**Objective**: Verify correct thumbnail for different sort modes

**Steps**:
1. Change sort mode to "Last Modified"
2. Verify folders reorder but thumbnails stay correct
3. Change sort mode to "Alphabetically"
4. Verify same

**Expected Result**:
- ✅ Sorting changes folder order
- ✅ Thumbnails remain associated with correct folders
- ✅ No mixing of thumbnails

---

## Edge Case Testing

### Edge Case 1: Very Large Images
**Setup**: Folder with 10MB+ images

**Expected**: 
- ✅ Loads without crash
- ✅ Coil handles sizing automatically

### Edge Case 2: Corrupted Image File
**Setup**: Image file that can't be decoded

**Expected**:
- ✅ No crash
- ✅ Shows placeholder or loads next image
- ✅ Error logged but handled gracefully

### Edge Case 3: Missing Image File
**Setup**: File path in database but file deleted

**Expected**:
- ✅ No crash
- ✅ Shows placeholder
- ✅ Log shows "exists: false"

### Edge Case 4: Special Characters in Filenames
**Setup**: Images with unicode, emojis, etc.

**Expected**:
- ✅ Loads correctly
- ✅ No encoding issues

### Edge Case 5: Simultaneous Updates
**Setup**: Add screenshots while FolderScreen open

**Expected**:
- ✅ Updates reflected after refresh
- ✅ No crashes from concurrent access

---

## Regression Testing

Ensure these features still work:

- [ ] Folder creation
- [ ] Folder deletion
- [ ] Folder renaming
- [ ] Custom folder icons
- [ ] Pinned folders
- [ ] Folder navigation
- [ ] Screenshot drag-and-drop to folders
- [ ] Favorite screenshots
- [ ] Trash/restore functionality

---

## Logging Verification Checklist

### Required Logs (All should be present)
- [ ] FolderScreen: After refresh message
- [ ] FolderScreen: Album list with thumbnail paths
- [ ] AlbumGridItem: Rendering messages with item IDs
- [ ] GlideImage: File loading messages with exists status

### No Unwanted Logs (Should NOT appear)
- [ ] E/GlideImage: Error messages
- [ ] E/FolderScreen: Exception stack traces
- [ ] Memory warnings
- [ ] Thread exceptions

---

## Performance Benchmarks

### Acceptable Metrics
| Metric | Target | Max |
|--------|--------|-----|
| Initial Load Time | 1000ms | 2000ms |
| Thumbnail Load Time (per image) | 100ms | 300ms |
| Memory (FolderScreen) | 30MB | 100MB |
| Scroll Frame Rate | 60fps | 50fps |
| Grid Render Time | 500ms | 1000ms |

### Measurement Commands
```bash
# Performance profiling
adb shell am start -W com.aks_labs.pixelflow/.ui.screens.MainActivity

# Memory usage
adb shell dumpsys meminfo com.aks_labs.pixelflow

# FPS monitoring
adb shell dumpsys SurfaceFlinger
```

---

## Bug Report Template

If issues found, use this template:

```
Bug: Thumbnails Not Displaying

Device: [Model, Android Version]
App Version: [Version]
Reproduction Steps:
1. ...
2. ...
3. ...

Expected: [What should happen]
Actual: [What happens instead]

Logs:
[Relevant logcat output]

Screenshots:
[If applicable]
```

---

## Sign-Off

### Developer Testing
- [ ] All tests passed
- [ ] No new errors in logcat
- [ ] Memory usage acceptable
- [ ] Performance acceptable

### QA Testing
- [ ] Manual testing complete
- [ ] Edge cases tested
- [ ] Regression tests passed
- [ ] Ready for release

### Sign-Off
- Developer: ________ Date: ________
- QA: ________ Date: ________
- PM: ________ Date: ________

---

## Test Data Setup

### Sample Test Folders
Create these for testing:

```
/storage/emulated/0/Pictures/PixelFlow/
├── Screenshots/ (3-5 images)
├── Downloads/ (2-3 images)
├── Documents/ (1-2 images)
├── Archive/ (0 images - empty)
└── Custom/ (10+ images - large test)
```

### Sample Screenshot IDs and Folder IDs
```
Folder: Screenshots (id=1)
  Screenshot: 101, 102, 103

Folder: Downloads (id=2)
  Screenshot: 201, 202

Folder: Documents (id=3)
  Screenshot: 301

Folder: Archive (id=4)
  (No screenshots)

Folder: Custom (id=5)
  Screenshot: 501, 502, ... 515
```

### Sample Paths
```
filePath=/storage/emulated/0/Pictures/PixelFlow/Screenshots/image_2024_01_15_001.jpg
thumbnailPath=/storage/emulated/0/Pictures/PixelFlow/Screenshots/.thumbnail/image_2024_01_15_001.jpg
```

---

## Automated Testing (Future)

For regression testing, create instrumentation tests:

```kotlin
@Test
fun testThumbnailsLoad() {
    // 1. Open FolderScreen
    // 2. Wait for thumbnails to load
    // 3. Verify albumToThumbnailMapping is populated
    // 4. Assert all visible folders have images
}

@Test
fun testEmptyFolderHandling() {
    // 1. Create empty folder
    // 2. Open FolderScreen
    // 3. Assert no crash
    // 4. Assert placeholder shown
}

@Test
fun testThumbnailUpdateOnNewScreenshot() {
    // 1. Record initial thumbnail ID
    // 2. Add new screenshot
    // 3. Refresh FolderScreen
    // 4. Assert thumbnail ID changed
}
```

---

## Completion Criteria

Feature is COMPLETE when:
- ✅ All test scenarios pass
- ✅ No critical bugs found
- ✅ Performance acceptable
- ✅ Logcat clean (no errors/warnings)
- ✅ Documentation complete
- ✅ Code review approved
