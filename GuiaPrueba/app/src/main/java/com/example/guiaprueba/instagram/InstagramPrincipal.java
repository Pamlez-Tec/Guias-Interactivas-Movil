package com.example.guiaprueba.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guiaprueba.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Scanner;


public class InstagramPrincipal extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> posts;


    private View guideOverlay;
    private TextView guideText;
    private ImageButton btnClose, btnBack, btnNext;
    private int currentStep = 0;
    private final String[] guideSteps = {
            "A continuación aprenderá cómo hacer una publicación en Instagram," +
                    " la cual te permite compartir una imagen junto " +
                    "a un comentario que desees aportar.",
            "Para poder realizar tu propia publicación debes presionar " +
                    "el botón con simbólo + que se encuentra en la mitad de" +
                    " la barra inferior"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram_principal);

        recyclerView = findViewById(R.id.recyclerViewPosts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        posts = loadPostsFromFile();
        postAdapter = new PostAdapter(this, posts);
        recyclerView.setAdapter(postAdapter);
        Intent intentAux = getIntent();

        // Intentamos obtener el valor "userId"
        int userId = intentAux.getIntExtra("userId", -1); // Valor por defecto -1 si no se pasó nada
        if (userId == 1) {
            showGuide();}

        // Configurar BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                // Acción para "Inicio"
                return true;
            } else if (itemId == R.id.nav_search) {
                // Acción para "Buscar"
                return true;
            } else if (itemId == R.id.nav_add) {
                Intent intent = new Intent(InstagramPrincipal.this, GalleryActivity.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.nav_notifications) {
                // Acción para "Notificaciones"
                return true;
            } else if (itemId == R.id.nav_profile) {
                // Acción para "Perfil"
                return true;
            }

            return false;
        });

        // Configurar toolbar
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> finish()); // o alguna otra acción

        // Click en el boton guia
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_star) {
                showGuide();
                return true;
            }
            return false;
        });
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
        params.topMargin = 1000; // posicion y
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

    private List<Post> loadPostsFromFile() {
        File file = new File(getFilesDir(), "posts.json");
        String filePath = file.getAbsolutePath();
        Log.d("Ruta del archivo", filePath);

        if (!file.exists()) {
            try (InputStream inputStream = getAssets().open("posts.json");
                 FileOutputStream outputStream = new FileOutputStream(file)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                Log.d("InstagramPrincipal", "Archivo 'posts.json' copiado correctamente desde assets.");
            } catch (IOException e) {
                Log.e("InstagramPrincipal", "Error al copiar el archivo desde assets", e);
                return null;
            }
        }

        try {
            Scanner scanner = new Scanner(file).useDelimiter("\\A");
            String json = scanner.hasNext() ? scanner.next() : "";

            Gson gson = new Gson();
            Type postListType = new TypeToken<List<Post>>() {}.getType();
            return gson.fromJson(json, postListType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
