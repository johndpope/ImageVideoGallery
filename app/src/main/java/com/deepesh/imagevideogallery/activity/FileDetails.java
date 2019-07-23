package com.deepesh.imagevideogallery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.deepesh.imagevideogallery.R;

import java.net.URISyntaxException;

public class FileDetails extends AppCompatActivity {

    ImageView imageView;
    TextView file_name;
    TextView file_cat;
    TextView file_price;

    int imgloc,imageprice;
    String imagename,imagecat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_details);

        imageView=findViewById(R.id.image_view);
        file_name=findViewById(R.id.file_name);
        file_cat=findViewById(R.id.file_cat);
        file_price=findViewById(R.id.file_price);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            imgloc = extras.getInt("imgloc");
            imagename=extras.getString("imgname");
            imagecat=extras.getString("imgcat");
            imageprice=extras.getInt("imgprice");
        }
        //Toast.makeText(FileDetails.this, "Image Location : "+imgloc, Toast.LENGTH_SHORT).show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Glide.with(getApplicationContext()).load(imgloc).into(imageView);
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
