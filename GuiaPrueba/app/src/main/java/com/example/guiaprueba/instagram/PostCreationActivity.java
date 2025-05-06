package com.example.guiaprueba.instagram;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.guiaprueba.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PostCreationActivity extends AppCompatActivity {

    private ImageView imagePreview;
    private EditText captionInput;
    private Uri imageUri;
    private static final String FILE_NAME = "posts.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_creation);

        imagePreview = findViewById(R.id.imagePreview);
        captionInput = findViewById(R.id.captionInput);
        Button saveButton = findViewById(R.id.saveButton);

        String uriString = getIntent().getStringExtra("imageUri");
        if (uriString != null) {
            imageUri = Uri.parse(uriString);
            imagePreview.setImageURI(imageUri);
        }

        saveButton.setOnClickListener(v -> savePost());
    }

    private void savePost() {
        String caption = captionInput.getText().toString();
        String username = "guest"; // Valor fijo

        if (imageUri == null || caption.isEmpty()) {
            Toast.makeText(this, "Selecciona una imagen y escribe un caption.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Guardar imagen en almacenamiento interno
            String imageFileName = "img_" + System.currentTimeMillis() + ".jpg";
            File imageFile = new File(getFilesDir(), imageFileName);
            try (InputStream inputStream = getContentResolver().openInputStream(imageUri);
                 OutputStream outputStream = new FileOutputStream(imageFile)) {
                byte[] buffer = new byte[4096];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
            }

            // Cargar y actualizar JSON
            File jsonFile = new File(getFilesDir(), FILE_NAME);
            Gson gson = new Gson();
            List<Post> postList;

            if (jsonFile.exists()) {
                InputStream inputStream = openFileInput(FILE_NAME);
                Type listType = new TypeToken<List<Post>>() {}.getType();
                postList = gson.fromJson(new java.util.Scanner(inputStream).useDelimiter("\\A").next(), listType);
                inputStream.close();
            } else {
                postList = new ArrayList<>();
            }

            // Crear post
            int newId = postList.isEmpty() ? 1 : postList.get(postList.size() - 1).getId() + 1;
            Post newPost = new Post(newId, username, caption, imageFileName);
            postList.add(newPost);

            // Guardar JSON
            try (FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE)) {
                String json = gson.toJson(postList);
                fos.write(json.getBytes());
            }

            Toast.makeText(this, "Post guardado", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al guardar el post", Toast.LENGTH_SHORT).show();
        }
    }
}
