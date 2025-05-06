package com.example.guiaprueba.instagram;

import android.content.Context;
import android.graphics.Bitmap;
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
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private Context context;
    private List<Post> postList;

    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
        // Ordenar los posts para que los más recientes aparezcan primero
        Collections.sort(this.postList, (post1, post2) -> Integer.compare(post2.getId(), post1.getId()));
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

        // Intentar cargar la imagen desde los assets primero
        try {
            InputStream assetInputStream = context.getAssets().open("gallery_images/" + imageName + ".jpg");
            Bitmap bitmap = android.graphics.BitmapFactory.decodeStream(assetInputStream);
            holder.imagePost.setImageBitmap(bitmap);
        } catch (Exception e) {
            Log.d("Image Path", "No se pudo cargar desde assets: " + e.getMessage());
            // Si no se encuentra en assets, intentar cargarla desde los recursos 'drawable'
            int imageResId = context.getResources().getIdentifier(
                    imageName, // El nombre de la imagen sin la extensión
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
                File imageFile = new File(context.getFilesDir(), imageName + ".jpg");
                Log.d("Image Path", "Ruta del archivo: " + imageFile.getAbsolutePath());
                if (imageFile.exists()) {
                    // Si la imagen está en el almacenamiento interno
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
        // Ordenar los nuevos posts para que los más recientes aparezcan primero
        Collections.sort(this.postList, (post1, post2) -> Integer.compare(post2.getId(), post1.getId()));
        notifyDataSetChanged();
    }
}
