package com.deepesh.imagevideogallery.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.deepesh.imagevideogallery.R;
import com.deepesh.imagevideogallery.adapter.RecyclerViewAdapter;
import com.deepesh.imagevideogallery.model.Details;
import com.deepesh.imagevideogallery.model.MyData;
import com.deepesh.imagevideogallery.model.RecyclerItemClickListener;
import com.deepesh.imagevideogallery.model.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    ArrayList<Details> dataList,datasrchlist;;
    LinearLayoutManager manager;
    MyData data;
    Details details;
    int pos;
    EditText srchtxt;

    List<HashMap<String,String>> fileList;

    FirebaseAuth fauth;

    String FILE_TYPE="fhhfjdk";

    ProgressDialog progressDialog;
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = fauth.getCurrentUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressDialog=new ProgressDialog(HomeActivity.this);
        recyclerView=findViewById(R.id.recycler_view);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            FILE_TYPE = extras.getString("FILE_TYPE");
        }

        dataList=new ArrayList<>();
        datasrchlist=new ArrayList<>();

        fauth=FirebaseAuth.getInstance();
        //prepareData();
        retrieveData();

        //Toast.makeText(HomeActivity.this, "FILE_TYPE = "+FILE_TYPE, Toast.LENGTH_SHORT).show();

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(HomeActivity.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //deta=dataList.get(position);
                details=dataList.get(position);
                pos=position;
                //showOption();

                Intent intent=new Intent(HomeActivity.this,FileDetails.class);
                intent.putExtra("imgloc",details.getUrl());
                intent.putExtra("imgname",details.getName());
                intent.putExtra("imgprice",details.getPrice());
                intent.putExtra("imgcat",details.getCat());
                intent.putExtra("FILE_TYPE",FILE_TYPE);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        manager = new LinearLayoutManager(getApplicationContext());

        //datasrchlist.addAll(dataList);
        adapter=new RecyclerViewAdapter(HomeActivity.this,R.layout.list_item, dataList);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);



        srchtxt=findViewById(R.id.eTxtSearch);
        srchtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text=srchtxt.getText().toString().toLowerCase(Locale.getDefault());
                filter(text);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }


    private void retrieveData(){

        progressDialog.setMessage("Loading...");
        progressDialog.show();
        fauth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                String FOLDER_NAME="";
                if (FILE_TYPE.equalsIgnoreCase("images")){
                    FOLDER_NAME=Util.IMAGE_VIEW;
                }else if (FILE_TYPE.equalsIgnoreCase("videos")){
                    FOLDER_NAME=Util.VIDEO_VIEW;
                }else {
                    FOLDER_NAME=Util.IMAGE_VIEW;
                }


                DatabaseReference dref=FirebaseDatabase.getInstance().getReference();
                dref.child(FOLDER_NAME).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //fileList=new ArrayList<>();
                        if (dataSnapshot.exists()){
                            for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                                Details details=snapshot.getValue(Details.class);
                                String imagename=details.getName();
                                String imagecat=details.getCat();
                                String imagepriice=details.getPrice();
                                String imageurl=details.getUrl();

                                Details details1=new Details(imagename,imageurl,imagecat,imagepriice);
                                dataList.add(details1);
                            }

                        }
                        datasrchlist.addAll(dataList);
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        progressDialog.dismiss();
                    }
                });
            }
        });


    }


    private void filter(String text) {
        text=text.toLowerCase(Locale.getDefault());
        dataList.clear();
        if (text.length()==0){
            dataList.addAll(datasrchlist);
        }else {
            for (Details d:datasrchlist){
                if (d.getCat().toLowerCase(Locale.getDefault()).contains(text)){
                    dataList.add(d);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_upload) {
            Intent intent=new Intent(getApplicationContext(),FileUpload.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent=new Intent(HomeActivity.this,HomeActivity.class);
            FILE_TYPE=Util.IMAGE_VIEW;
            intent.putExtra("FILE_TYPE",FILE_TYPE);
            startActivity(intent);
        } else if (id == R.id.nav_video) {
            Intent intent=new Intent(HomeActivity.this,HomeActivity.class);
            FILE_TYPE=Util.VIDEO_VIEW;
            intent.putExtra("FILE_TYPE",FILE_TYPE);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_login) {
            Intent intent=new Intent(getApplicationContext(),Login.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
