package com.example.guiaprueba.instagram;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guiaprueba.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<String> imageNames = new ArrayList<>(); // nombres de archivos dentro de assets/images

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        recyclerView = findViewById(R.id.recyclerViewGallery);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        loadAssetImageNames();
    }

    private void loadAssetImageNames() {
        try {
            String[] files = getAssets().list("gallery_images");
            if (files != null) {
                imageNames = Arrays.asList(files);
                recyclerView.setAdapter(new GalleryAssetAdapter(imageNames, imageName -> {
                    Intent intent = new Intent(this, PostCreationActivity.class);
                    intent.putExtra("imageName", imageName);

                    startActivity(intent);
                }));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
