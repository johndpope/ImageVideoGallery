package com.deepesh.imagevideogallery.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.deepesh.imagevideogallery.R;

import java.io.IOException;

public class FileUpload extends AppCompatActivity implements View.OnClickListener {

    Button btnImageChoose,btnImageUpload;
    int Image_Request_Code = 7;
    Uri FilePathUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_upload);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnImageChoose=findViewById(R.id.btnImageChoose);
        btnImageUpload=findViewById(R.id.btnImageUpload);

        btnImageChoose.setOnClickListener(this);
        btnImageUpload.setOnClickListener(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);

                // After selecting image change choose button above text.
                btnImageChoose.setText("Image Selected...!");

            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
//    public void UploadImageFileToFirebaseStorage() {
//        // Checking whether FilePathUri Is empty or not.
//        if (FilePathUri != null) {
//            progressDialog.setMessage("Uploading...");
//            progressDialog.show();
//            StorageReference storageReference2nd = storageReference.child("photos").child("profilepic"+ System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
//
//            // Adding addOnSuccessListener to second StorageReference.
//            storageReference2nd.putFile(FilePathUri)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                            progressDialog.dismiss();
//                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
//
//                            // Getting image upload ID.
//                            imgUrl = taskSnapshot.getDownloadUrl().toString();
//
//                        }
//                    })
//                    //If something goes wrong.
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception exception) {
//
//                            // Hiding the progressDialog.
//                            progressDialog.dismiss();
//
//                            // Showing exception erro message.
//                            Toast.makeText(RegisterActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    })
//
//                    // On progress change upload time.
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//
//                            // Setting progressDialog Title.
//                            progressDialog.setMessage("Uploading...");
//
//                        }
//                    });
//
//        }
//        else {
//
//            Toast.makeText(RegisterActivity.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();
//
//        }
//    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.btnImageChoose:
                Intent intent2=new Intent();
                intent2.setType("image/*");
                intent2.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent2, "Please Select Image"), Image_Request_Code);
                //Toast.makeText(RegisterActivity.this, "Clicked On ChooseImage", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnImageUpload:
                //UploadImageFileToFirebaseStorage();
                Toast.makeText(FileUpload.this, "Clicked On UploadImage", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
