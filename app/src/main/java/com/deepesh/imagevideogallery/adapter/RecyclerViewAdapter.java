package com.deepesh.imagevideogallery.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deepesh.imagevideogallery.R;
import com.deepesh.imagevideogallery.model.MyData;

import java.util.ArrayList;

/**
 * Created by Deepesh on 02-10-2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    Context context;
    int resource;
    ArrayList<MyData> list;

    int lastPosition=-1;
    public RecyclerViewAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<MyData> list) {

        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(resource,parent,false);
        ViewHolder holder=new ViewHolder(view);

        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        MyData data=list.get(position);
        holder.txtname.setText(data.getName()+"   "+data.getHashtag()+"  "+data.getPrice());
        Glide.with(context).load(data.getThumbnamil()).into(holder.thumbnail);

        if(position >lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context,
                    android.R.anim.slide_in_left);
            holder.itemView.startAnimation(animation);
            lastPosition = position;
        }

    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtname;
        ImageView thumbnail;
        public ViewHolder(View itemView) {
            super(itemView);
            thumbnail=(ImageView)itemView.findViewById(R.id.image_view);
            txtname=(TextView)itemView.findViewById(R.id.image_name);


        }
    }
}
