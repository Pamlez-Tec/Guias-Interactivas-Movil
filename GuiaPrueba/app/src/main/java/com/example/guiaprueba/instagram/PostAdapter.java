package com.example.guiaprueba.instagram;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.guiaprueba.R;

import java.io.File;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private Context context;
    private List<Post> postList;

    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.textUsername.setText(post.getUsername());
        holder.textCaption.setText(post.getCaption());

        String imageName = post.getImage(); // Asumimos que es el nombre de la imagen

        // Primero intentamos cargar la imagen desde recursos 'drawable'
        int imageResId = context.getResources().getIdentifier(
                imageName.replace(".jpg", ""),
                "drawable",
                context.getPackageName()
        );

        if (imageResId != 0) {
            // Si la imagen está en drawable
            Glide.with(context)
                    .load(imageResId)
                    .placeholder(R.drawable.placeholder)
                    .into(holder.imagePost);
        } else {
            // Si no está en drawable, intentamos cargarla desde el almacenamiento interno

            File imageFile = new File(context.getFilesDir(), imageName);
            Log.d("Image Path", "Ruta del archivo: " + imageFile.getAbsolutePath());
            if (imageFile.exists()) {
                Glide.with(context)
                        .load(Uri.fromFile(imageFile))
                        .placeholder(R.drawable.placeholder)
                        .into(holder.imagePost);
            } else {
                // Si no encontramos el archivo, mostramos el placeholder
                holder.imagePost.setImageResource(R.drawable.placeholder);
            }
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView textUsername;
        ImageView imagePost;
        TextView textCaption;

        public PostViewHolder(View itemView) {
            super(itemView);
            textUsername = itemView.findViewById(R.id.textUsername);
            imagePost = itemView.findViewById(R.id.imagePost);
            textCaption = itemView.findViewById(R.id.textCaption);
        }
    }

    public void updatePosts(List<Post> newPosts) {
        this.postList = newPosts;
        notifyDataSetChanged();
    }
}
