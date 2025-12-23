# Lab 4 – Media and Device Integration

## Theme
**Profile Picture Updater**  
This app allows users to take a photo using the device Camera or select an image from the Gallery and preview it directly within the app.

## Architecture (MVC)
This application follows the **Model–View–Controller (MVC)** architecture.  
The **View** is defined using XML layouts (`activity_main.xml`) that handle the UI components such as buttons and the ImageView.  
The **Controller** is implemented in `MainActivity.java`, which manages user interactions, runtime permissions, and camera/gallery intents.  
The **Model** consists of the selected image (stored as a thumbnail Bitmap or Gallery Uri) and is preserved across configuration changes using the activity lifecycle.

## Features
- Capture an image using the device Camera (built-in Android Intent)
- Select an image from the Gallery
- Runtime permission handling for Camera and Media access
- Displays selected or captured image in an ImageView
- Graceful handling of permission denial, cancel actions, and null results
- Restores the selected image on orientation changes



## Lab4- created by:  
- Shaqayeq Salimy
