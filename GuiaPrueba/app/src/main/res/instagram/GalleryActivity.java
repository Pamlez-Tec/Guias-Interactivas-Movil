package com.example.guiaprueba.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guiaprueba.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<String> imageNames = new ArrayList<>(); // nombres de archivos dentro de assets/images
    private View guideOverlay;
    private TextView guideText;
    private ImageButton btnClose, btnBack, btnNext;
    private int currentStep = 0;
    private final String[] guideSteps = {
            "Esta es la galería, en ella puede elegir la foto que desea publicar. " +
                    "Las fotos que utiliza este tutorial son de prueba, en la " +
                    "apliación real puede elegir sus propias fotos",
            "Por favor seleccione una foto para continuar"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        recyclerView = findViewById(R.id.recyclerViewGallery);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        // Configurar toolbar
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> finish()); // o alguna otra acción
        showGuide();
        // Click en el boton guia
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_star) {
                showGuide();
                return true;
            }
            return false;
        });
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
    private void showGuide() {
        FrameLayout rootLayout = findViewById(android.R.id.content);
        LayoutInflater inflater = LayoutInflater.from(this);
        guideOverlay = inflater.inflate(R.layout.layout_guide_overlay, rootLayout, false);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        params.topMargin = 1000;
        guideOverlay.setLayoutParams(params);

        rootLayout.addView(guideOverlay);

        guideText = guideOverlay.findViewById(R.id.guideText);
        btnClose = guideOverlay.findViewById(R.id.btnClose);
        btnBack = guideOverlay.findViewById(R.id.btnBack);
        btnNext = guideOverlay.findViewById(R.id.btnNext);

        updateStep();

        btnClose.setOnClickListener(v -> guideOverlay.setVisibility(View.GONE));
        btnBack.setOnClickListener(v -> {
            if (currentStep > 0) {
                currentStep--;
                updateStep();
            }
        });
        btnNext.setOnClickListener(v -> {
            if (currentStep < guideSteps.length - 1) {
                currentStep++;
                updateStep();
            }
        });

        guideOverlay.setVisibility(View.VISIBLE);
    }

    private void updateStep() {
        guideText.setText(guideSteps[currentStep]);
        btnBack.setVisibility(currentStep == 0 ? View.GONE : View.VISIBLE);
        btnNext.setVisibility(currentStep == guideSteps.length - 1 ? View.GONE : View.VISIBLE);
    }
}
