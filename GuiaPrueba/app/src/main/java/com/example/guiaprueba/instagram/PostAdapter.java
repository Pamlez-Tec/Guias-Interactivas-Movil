package com.example.guiaprueba.instagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.guiaprueba.R;

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

        int imageResId = context.getResources().getIdentifier(
                post.getImage().replace(".jpg", ""),
                "drawable",
                context.getPackageName()
        );

        Glide.with(context)
                .load(imageResId)
                .placeholder(R.drawable.placeholder)
                .into(holder.imagePost);
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
