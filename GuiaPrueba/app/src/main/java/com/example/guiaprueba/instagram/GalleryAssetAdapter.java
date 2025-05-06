package com.example.guiaprueba.instagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.guiaprueba.R;

import java.util.List;

public class GalleryAssetAdapter extends RecyclerView.Adapter<GalleryAssetAdapter.ViewHolder> {

    private List<String> imageNames;
    private OnImageClickListener listener;

    public interface OnImageClickListener {
        void onImageClick(String imageName);
    }

    public GalleryAssetAdapter(List<String> imageNames, OnImageClickListener listener) {
        this.imageNames = imageNames;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_gallery_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String imageName = imageNames.get(position);
        String assetPath = "file:///android_asset/gallery_images/" + imageName;

        Glide.with(holder.itemView.getContext())
                .load(assetPath)
                .placeholder(R.drawable.placeholder)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(v -> listener.onImageClick(imageName));
    }

    @Override
    public int getItemCount() {
        return imageNames.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewGallery);
        }
    }

}
