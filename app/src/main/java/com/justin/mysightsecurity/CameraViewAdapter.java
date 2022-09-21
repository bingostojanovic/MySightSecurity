package com.justin.mysightsecurity;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CameraViewAdapter  extends RecyclerView.Adapter<CameraViewAdapter.ViewHolder>{

    private List<String> mimagePath;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public CameraViewAdapter(Context context, List<String> mimagePath) {
        this.mInflater = LayoutInflater.from(context);
        this.mimagePath = mimagePath;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.camera_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        int color = mViewColors.get(position);
//        String animal = mAnimals.get(position);
//        holder.myView.setBackgroundColor(color);
//        holder.myTextView.setText(animal);

        String imagePath = mimagePath.get(position);
        Uri uri = Uri.parse(imagePath);

        holder.imageView.setImageURI(uri);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mimagePath.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.camera_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mimagePath.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

