package com.example.guiaprueba.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guiaprueba.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram_principal);


        recyclerView = findViewById(R.id.recyclerViewPosts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        posts = loadPostsFromFile();
        postAdapter = new PostAdapter(this, posts);
        recyclerView.setAdapter(postAdapter);
        // Configurar BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Establecer un listener para manejar la selección de ítems
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

    }

    private List<Post> loadPostsFromFile() {
        File file = new File(getFilesDir(), "posts.json");
        String filePath = file.getAbsolutePath();

        // Mostrar la dirección en el log
        Log.d("Ruta del archivo", filePath);

        // Si el archivo no existe en el almacenamiento interno, copiarlo desde assets
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
        } else {
            Log.d("InstagramPrincipal", "El archivo 'posts.json' ya existe en el directorio de archivos.");
        }

        try {
            Scanner scanner = new Scanner(file).useDelimiter("\\A");
            String json = scanner.hasNext() ? scanner.next() : "";

            Gson gson = new Gson();
            Type postListType = new TypeToken<List<Post>>() {
            }.getType();
            return gson.fromJson(json, postListType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

