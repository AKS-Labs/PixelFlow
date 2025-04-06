# PixelFlow

PixelFlow is an Android app that enhances your screenshot management experience. It detects screenshots, creates draggable floating bubbles with the screenshot as a thumbnail, and allows organizing screenshots into customizable folders displayed as circular zones in a semi-circular pattern.

## Features

- **Screenshot Detection**: Automatically detects when you take a screenshot
- **Floating Bubbles**: Creates a draggable floating bubble with the screenshot thumbnail
- **Circular Drag Zones**: Organize screenshots by dragging bubbles to folder zones arranged in a semi-circular pattern
- **Customizable Folders**: Create, edit, and delete custom folders for organizing screenshots
- **Screenshot History**: View and manage all your screenshots in one place

## Default Folders

The app comes with five default folders:
- Quotes
- Tricks
- Images
- Posts
- Trash

## Technical Details

- Built with Kotlin and Jetpack Compose
- Uses SharedPreferences for data persistence
- Implements foreground service for screenshot detection
- Supports Android 8.0 (API 26) and above

## Getting Started

1. Clone the repository
2. Open the project in Android Studio
3. Build and run the app on your device

## Permissions

The app requires the following permissions:
- Storage access (to detect and manage screenshots)
- Draw over other apps (to display the floating bubble)
- Foreground service (to run in the background)

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Developed by AKS Labs
