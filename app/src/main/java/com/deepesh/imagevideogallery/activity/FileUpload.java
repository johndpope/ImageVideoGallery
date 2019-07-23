package com.deepesh.imagevideogallery.activity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.deepesh.imagevideogallery.R;
import com.deepesh.imagevideogallery.model.Details;
import com.deepesh.imagevideogallery.model.MyData;
import com.deepesh.imagevideogallery.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class FileUpload extends AppCompatActivity implements View.OnClickListener {

    Button btnImageChoose,btnImageUpload,btn_upload;
    int Image_Request_Code = 7;
    Uri FilePathUri;
    EditText image_name;
    EditText image_cat;
    EditText image_price;
    EditText type;

    FirebaseUser fuser;
    DatabaseReference dref;
    StorageReference sref;
    FirebaseAuth fauth;

    Details details;

    String imageName,imageCat,imagePrice,imageOrVideoUrl,d_type;
    ProgressDialog progressDialog;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = fauth.getCurrentUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_upload);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressDialog=new ProgressDialog(FileUpload.this);

        btnImageChoose=findViewById(R.id.btnImageChoose);
        btnImageUpload=findViewById(R.id.btnImageUpload);
        image_name=findViewById(R.id.image_name);
        image_cat=findViewById(R.id.image_cat);
        image_price=findViewById(R.id.image_price);
        type=findViewById(R.id.type);
        btn_upload=findViewById(R.id.upload);

        btnImageChoose.setOnClickListener(this);
        btnImageUpload.setOnClickListener(this);
        btn_upload.setOnClickListener(this);

        FirebaseApp.initializeApp(this);
        sref=FirebaseStorage.getInstance().getReference();
        dref=FirebaseDatabase.getInstance().getReference();
        fauth=FirebaseAuth.getInstance();

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
                btnImageChoose.setText("File Selected...!");

            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }
    public void UploadImageFileToFirebaseStorage() {
        // Checking whether FilePathUri Is empty or not.
        String imageorvideoFolderName="";
        if (GetFileExtension(FilePathUri).equalsIgnoreCase("jpg")
                ||GetFileExtension(FilePathUri).equalsIgnoreCase("jpeg")
                ||GetFileExtension(FilePathUri).equalsIgnoreCase("png")
                ||GetFileExtension(FilePathUri).equalsIgnoreCase("bmp")
                ||GetFileExtension(FilePathUri).equalsIgnoreCase("svg")
                ){
                type.setText("image");
            imageorvideoFolderName="Images";
        }else if(GetFileExtension(FilePathUri).equalsIgnoreCase("mp4")
                ||GetFileExtension(FilePathUri).equalsIgnoreCase("avi")
                ||GetFileExtension(FilePathUri).equalsIgnoreCase("mov")
                ||GetFileExtension(FilePathUri).equalsIgnoreCase("3gp")
                ||GetFileExtension(FilePathUri).equalsIgnoreCase("wmv")
                ){
                type.setText("video");
            imageorvideoFolderName="Videos";
        }
        if (FilePathUri != null) {
            progressDialog.setMessage("Uploading...");
            progressDialog.show();

            Toast.makeText(getApplicationContext(), "Fille Extension "+GetFileExtension(FilePathUri), Toast.LENGTH_LONG).show();
            StorageReference storageReference2nd = sref.child(imageorvideoFolderName).child(type.getText().toString().trim()+ System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));

            // Adding addOnSuccessListener to second StorageReference.

            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            progressDialog.dismiss();
                            //Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();

                            // Getting image upload ID.
                            Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                            task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imageOrVideoUrl = uri.toString();
                                }
                            });

                        }
                    })
                    //If something goes wrong.
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            // Hiding the progressDialog.
                            progressDialog.dismiss();

                            // Showing exception erro message.
                            Toast.makeText(FileUpload.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })

                    // On progress change upload time.
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            // Setting progressDialog Title.
                            progressDialog.setMessage("Uploading...");

                        }
                    });

        }
        else {

            Toast.makeText(FileUpload.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }

    public void uploadDetails(){
        if (!validateForm()) {
            return;
        }

        progressDialog.setMessage("Creating....");
        progressDialog.show();

        imageName=image_name.getText().toString().trim();
        imageCat=image_cat.getText().toString().trim();
        imagePrice=image_price.getText().toString().trim();
        d_type=type.getText().toString().trim();
        Toast.makeText(getApplicationContext(), "Fille Type "+d_type, Toast.LENGTH_LONG).show();

        String imageOrVideo="";
        if(d_type.equalsIgnoreCase("image")){
            imageOrVideo="Images";
        }else if(d_type.equalsIgnoreCase("video")){
            imageOrVideo="Videos";
        }


        try {
            final String finalImageOrVideo = imageOrVideo;
            fauth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){


                        fuser=fauth.getCurrentUser();
                        details=new Details(imageName,imageOrVideoUrl,imageCat,imagePrice);

                        Toast.makeText(FileUpload.this, details.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("shs",details.toString());
                        dref.child(finalImageOrVideo).child(imageName).setValue(details);
                        Intent intent=new Intent(FileUpload.this, HomeActivity.class);
                        startActivity(intent);
                        progressDialog.dismiss();

                        Toast.makeText(FileUpload.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                    }else{
                        System.out.println("Not Uploaded");
                        Toast.makeText(FileUpload.this, "Upload UnSuccessful", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }catch (Exception e){
            Toast.makeText(FileUpload.this, "Upload UnSuccessful Due to Exception", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }


    private boolean validateForm() {
        boolean result = true;

        if (TextUtils.isEmpty(imageOrVideoUrl)) {
            btnImageChoose.setError("Required");
            result = false;
        } else {
            btnImageChoose.setError(null);
        }

        if (TextUtils.isEmpty(image_name.getText().toString())) {
            image_name.setError("Required");
            result = false;
        } else {
            image_name.setError(null);
        }

        if (TextUtils.isEmpty(image_cat.getText().toString())) {
            image_cat.setError("Required");
            result = false;
        } else {
            image_cat.setError(null);
        }
        if (TextUtils.isEmpty(image_price.getText().toString())) {
            image_price.setError("Required");
            result = false;
        } else {
            image_price.setError(null);
        }

        if (TextUtils.isEmpty(type.getText().toString())) {
            type.setError("Required");
            result = false;
        } else {
            type.setError(null);
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.btnImageChoose:
                Intent intent2=new Intent();
                intent2.setType("*/*");
                intent2.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent2, "Please Select Image"), Image_Request_Code);
                //Toast.makeText(RegisterActivity.this, "Clicked On ChooseImage", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnImageUpload:
                UploadImageFileToFirebaseStorage();
                //Toast.makeText(FileUpload.this, "Clicked On UploadImage", Toast.LENGTH_SHORT).show();
                break;
            case R.id.upload:
                uploadDetails();
                Toast.makeText(FileUpload.this, "Clicked On UploadData", Toast.LENGTH_SHORT).show();

        }
    }
}
