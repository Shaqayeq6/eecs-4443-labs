package com.example.lab4;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CAMERA = 1001;
    private static final int REQ_GALLERY = 1002;

    private static final int PERM_CAMERA = 2001;
    private static final int PERM_GALLERY = 2002;

    private ImageView ivPreview;
    private TextView tvStatus;

    // For rotation restore
    private Bitmap lastBitmap = null;
    private Uri lastImageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivPreview = findViewById(R.id.ivPreview);
        tvStatus = findViewById(R.id.tvStatus);

        Button btnCamera = findViewById(R.id.btnCamera);
        Button btnGallery = findViewById(R.id.btnGallery);

        // Restore image on rotation / recreation
        if (savedInstanceState != null) {
            lastBitmap = savedInstanceState.getParcelable("lastBitmap");
            String uriString = savedInstanceState.getString("lastImageUri");
            if (uriString != null) lastImageUri = Uri.parse(uriString);

            String status = savedInstanceState.getString("statusText");
            if (status != null) tvStatus.setText(status);

            if (lastBitmap != null) {
                ivPreview.setImageBitmap(lastBitmap);
            } else if (lastImageUri != null) {
                ivPreview.setImageURI(lastImageUri);
            }
        }

        btnCamera.setOnClickListener(v -> {
            if (hasCameraPermission()) {
                openCamera();
            } else {
                requestCameraPermission();
            }
        });

        btnGallery.setOnClickListener(v -> {
            if (hasGalleryPermission()) {
                openGallery();
            } else {
                requestGalleryPermission();
            }
        });
    }

    // --- Permissions helpers ---

    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                PERM_CAMERA);
    }

    private String galleryPermissionName() {
        // Android 13+ uses READ_MEDIA_IMAGES
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return Manifest.permission.READ_MEDIA_IMAGES;
        }
        return Manifest.permission.READ_EXTERNAL_STORAGE;
    }

    private boolean hasGalleryPermission() {
        return ContextCompat.checkSelfPermission(this, galleryPermissionName())
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestGalleryPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{galleryPermissionName()},
                PERM_GALLERY);
    }

    // --- Intents ---

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Safety: only launch if a camera app exists
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            tvStatus.setText("Opening camera...");
            startActivityForResult(cameraIntent, REQ_CAMERA);
        } else {
            tvStatus.setText("No camera app found.");
            Toast.makeText(this, "No camera app found.", Toast.LENGTH_SHORT).show();
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");

        tvStatus.setText("Opening gallery...");
        startActivityForResult(Intent.createChooser(galleryIntent, "Select an image"), REQ_GALLERY);
    }

    // --- Results (thumbnail camera + gallery uri) ---

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // User canceled → do not crash
        if (resultCode != RESULT_OK) {
            tvStatus.setText("Action canceled.");
            Toast.makeText(this, "Canceled.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (requestCode == REQ_CAMERA) {
            // Thumbnail bitmap comes from extras["data"]
            if (data != null && data.getExtras() != null && data.getExtras().get("data") != null) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                lastBitmap = thumbnail;
                lastImageUri = null;

                ivPreview.setImageBitmap(thumbnail);
                tvStatus.setText("Photo captured (thumbnail).");
            } else {
                tvStatus.setText("No image received from camera.");
                Toast.makeText(this, "No image received.", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == REQ_GALLERY) {
            if (data != null && data.getData() != null) {
                Uri imageUri = data.getData();
                lastImageUri = imageUri;
                lastBitmap = null;

                ivPreview.setImageURI(imageUri);
                tvStatus.setText("Image selected from gallery.");
            } else {
                tvStatus.setText("No image selected.");
                Toast.makeText(this, "No image selected.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // --- Permission callback ---

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean granted = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;

        if (requestCode == PERM_CAMERA) {
            if (granted) {
                tvStatus.setText("Camera permission granted.");
                openCamera();
            } else {
                tvStatus.setText("Camera permission denied.");
                Toast.makeText(this, "Camera permission denied.", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == PERM_GALLERY) {
            if (granted) {
                tvStatus.setText("Gallery permission granted.");
                openGallery();
            } else {
                tvStatus.setText("Gallery permission denied.");
                Toast.makeText(this, "Gallery permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // --- Save state for rotation ---

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("statusText", tvStatus.getText().toString());

        // Save either thumbnail bitmap OR gallery uri
        if (lastBitmap != null) {
            outState.putParcelable("lastBitmap", lastBitmap);
        }
        if (lastImageUri != null) {
            outState.putString("lastImageUri", lastImageUri.toString());
        }
    }
}
