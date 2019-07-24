package com.deepesh.imagevideogallery.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.deepesh.imagevideogallery.R;

import java.net.URISyntaxException;

public class FileDetails extends AppCompatActivity {

    ImageView imageView;
    VideoView videoView;
    TextView file_name;
    TextView file_cat;
    TextView file_price;

    MediaController mediaController;
    DisplayMetrics displayMetrics;

    String imagename,imagecat,imgloc,imageprice,FILE_TYPE="dghjfhjsfd";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_details);

        imageView=findViewById(R.id.image_view);
        videoView=findViewById(R.id.video_view);
        file_name=findViewById(R.id.file_name);
        file_cat=findViewById(R.id.file_cat);
        file_price=findViewById(R.id.file_price);

        mediaController=new MediaController(this);
        //displayMetrics=new DisplayMetrics();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            imgloc = extras.getString("imgloc");
            imagename=extras.getString("imgname");
            imagecat=extras.getString("imgcat");
            imageprice=extras.getString("imgprice");
            FILE_TYPE=extras.getString("FILE_TYPE");
        }
        //Toast.makeText(FileDetails.this, "File_Type : "+FILE_TYPE, Toast.LENGTH_SHORT).show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height=displayMetrics.heightPixels;
        int width=displayMetrics.widthPixels;
        videoView.setMinimumHeight(height);
        videoView.setMinimumWidth(width);*/

        if (FILE_TYPE.equalsIgnoreCase("videos")){
            imageView.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);
            Uri path=Uri.parse(imgloc);
            videoView.setVideoURI(path);
            videoView.setMediaController(mediaController);
            mediaController.setAnchorView(videoView);
            videoView.start();
        }else {
            videoView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(imgloc).into(imageView);
        }

        file_name.setText(imagename);
        file_price.setText(String.valueOf(imageprice));
        file_cat.setText(imagecat);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
