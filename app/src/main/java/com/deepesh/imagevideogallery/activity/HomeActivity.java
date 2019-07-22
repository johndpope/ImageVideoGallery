package com.deepesh.imagevideogallery.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.deepesh.imagevideogallery.model.MyData;
import com.deepesh.imagevideogallery.model.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    ArrayList<MyData> dataList,datasrchlist;
    LinearLayoutManager manager;
    MyData data;
    int pos;
    EditText srchtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView=findViewById(R.id.recycler_view);
        dataList=new ArrayList<>();
        datasrchlist=new ArrayList<>();
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(HomeActivity.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                data=dataList.get(position);
                pos=position;
                showOption();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        manager = new LinearLayoutManager(getApplicationContext());

        prepareData();

        datasrchlist.addAll(dataList);
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



    private void prepareData(){
        MyData data1=new MyData("img1","#Design1",R.drawable.a,1001);
        dataList.add(data1);
        MyData data2=new MyData("img2","#Design2",R.drawable.b,1002);
        dataList.add(data2);
        MyData data3=new MyData("img3","#Design3",R.drawable.c,1003);
        dataList.add(data3);
        MyData data4=new MyData("img4","#Design14",R.drawable.e,1004);
        dataList.add(data4);
        MyData data5=new MyData("img5","#Design15",R.drawable.f,1005);
        dataList.add(data5);
        MyData data6=new MyData("img6","#Design16",R.drawable.g,1006);
        dataList.add(data6);
        MyData data7=new MyData("img7","#Design17",R.drawable.h,1007);
        dataList.add(data7);
        MyData data8=new MyData("img8","#Design18",R.drawable.c,1008);
        dataList.add(data8);
        MyData data9=new MyData("img9","#Design19",R.drawable.f,1009);
        dataList.add(data9);

        MyData data10=new MyData("img1","#Design1",R.drawable.a,1001);
        dataList.add(data10);
        MyData data11=new MyData("img2","#Design2",R.drawable.b,1002);
        dataList.add(data11);
        MyData data12=new MyData("img3","#Design3",R.drawable.c,1003);
        dataList.add(data12);
        MyData data13=new MyData("img4","#Design14",R.drawable.e,1004);
        dataList.add(data13);
        MyData data15=new MyData("img5","#Design15",R.drawable.f,1005);
        dataList.add(data15);
        MyData data16=new MyData("img6","#Design16",R.drawable.g,1006);
        dataList.add(data16);
        MyData data17=new MyData("img7","#Design17",R.drawable.h,1007);
        dataList.add(data17);
        MyData data18=new MyData("img8","#Design18",R.drawable.c,1008);
        dataList.add(data18);
        MyData data19=new MyData("img9","#Design19",R.drawable.f,1009);
        dataList.add(data19);

        MyData data21=new MyData("img1","#Design1",R.drawable.a,1001);
        dataList.add(data21);
        MyData data22=new MyData("img2","#Design2",R.drawable.b,1002);
        dataList.add(data22);
        MyData data23=new MyData("img3","#Design3",R.drawable.c,1003);
        dataList.add(data23);
        MyData data24=new MyData("img4","#Design14",R.drawable.e,1004);
        dataList.add(data24);
        MyData data25=new MyData("img5","#Design15",R.drawable.f,1005);
        dataList.add(data25);
        MyData data26=new MyData("img6","#Design16",R.drawable.g,1006);
        dataList.add(data26);
        MyData data27=new MyData("img7","#Design17",R.drawable.h,1007);
        dataList.add(data27);
        MyData data28=new MyData("img8","#Design18",R.drawable.c,1008);
        dataList.add(data28);
        MyData data29=new MyData("img9","#Design19",R.drawable.f,1009);
        dataList.add(data29);

        MyData data31=new MyData("img1","#Design1",R.drawable.a,1001);
        dataList.add(data31);
        MyData data32=new MyData("img2","#Design2",R.drawable.b,1002);
        dataList.add(data32);
        MyData data33=new MyData("img3","#Design3",R.drawable.c,1003);
        dataList.add(data33);
        MyData data34=new MyData("img4","#Design14",R.drawable.e,1004);
        dataList.add(data34);
        MyData data35=new MyData("img5","#Design15",R.drawable.f,1005);
        dataList.add(data35);
        MyData data36=new MyData("img6","#Design16",R.drawable.g,1006);
        dataList.add(data36);
        MyData data37=new MyData("img7","#Design17",R.drawable.h,1007);
        dataList.add(data37);
        MyData data38=new MyData("img8","#Design18",R.drawable.c,1008);
        dataList.add(data38);
        MyData data39=new MyData("img9","#Design19",R.drawable.f,1009);
        dataList.add(data39);

    }

    void showOption() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        /*final String[] items = {"Buy item", "Add to cart", "Details"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:

                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                }
            }
        });*/
        builder.setTitle("Do you want to buy..?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(HomeActivity.this, "Thannk-you", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(HomeActivity.this, "Try Another", Toast.LENGTH_SHORT).show();
                    }
                }).create().show();
        //dialog.show();
    }


    private void filter(String text) {
        text=text.toLowerCase(Locale.getDefault());
        dataList.clear();
        if (text.length()==0){
            dataList.addAll(datasrchlist);
        }else {
            for (MyData d:datasrchlist){
                if (d.getHashtag().toLowerCase(Locale.getDefault()).contains(text)){
                    dataList.add(d);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }


    //-------------------------------------------------------------------------
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_upload) {
            Intent intent=new Intent(getApplicationContext(),FileUpload.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_video) {

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
    //-------------------------------------------------------------------------
}
