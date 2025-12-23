# Lab 4 – Media and Device Integration

## Theme
**Profile Picture Updater**  
This app allows user to take a picture with the device Camera. They can also pick a photo, from the Gallery. The app shows the picture away. People can see the photo they took with the Camera or picked from the Gallery directly in the app.

## Architecture (MVC)
  
This application is built using the **Model-View-Controller**. It is an idea that helps keep things organized.


The **View** is made using XML layouts like the one in (`activity_main.xml`). This is what takes care of the user interface things like buttons and the ImageView.
  
The **Controller** is, in the `MainActivity.java` file. It also takes care of getting the permissions when the program is running. The Controller manages what happens when the user wants to take a picture or pick one from the gallery.

The **Model** is made up of the picture you choose (stored as a thumbnail Bitmap or Gallery Uri). The Model remembers this picture when you change things on your phone like turning it sideways because it uses the activity lifecycle to keep everything safe.

## Features
- Capture an image using the device Camera (built-in Android Intent)
- Select an image from the Gallery
- Runtime permission handling for Camera and Media access
- Displays selected or captured image in an ImageView
- Graceful handling of permission denial, cancel actions, and null results
- Restores the selected image on orientation changes

## Lab4- created by:  
**Shaqayeq Salimy**
