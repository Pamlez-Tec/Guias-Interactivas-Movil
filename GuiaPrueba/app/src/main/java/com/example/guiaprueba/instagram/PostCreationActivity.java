package com.example.guiaprueba.instagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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
    private static final String FILE_NAME = "posts.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_creation);

        imagePreview = findViewById(R.id.imagePreview);
        captionInput = findViewById(R.id.captionInput);
        Button saveButton = findViewById(R.id.saveButton);

        // Recibir el nombre de la imagen desde la intent
        String imageName = getIntent().getStringExtra("imageName");
        if (imageName != null) {
            String assetPath = "file:///android_asset/gallery_images/" + imageName;

            // Cargar la imagen desde los assets
            try (InputStream is = getAssets().open("gallery_images/" + imageName)) {
                Bitmap bitmap = android.graphics.BitmapFactory.decodeStream(is);
                imagePreview.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "No se pudo cargar la imagen", Toast.LENGTH_SHORT).show();
            }
        }

        saveButton.setOnClickListener(v -> savePost());
    }

    private void savePost() {
        String caption = captionInput.getText().toString();
        String username = "guest"; // Valor fijo

        if (caption.isEmpty()) {
            Toast.makeText(this, "Escribe un caption.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Solo copiar la dirección de la imagen, no guardar la imagen
            String imageFileName = getIntent().getStringExtra("imageName").replace(".jpg", ""); // Eliminar la extensión .jpg

            // Cargar y actualizar el JSON
            File jsonFile = new File(getFilesDir(), FILE_NAME);
            Gson gson = new Gson();
            List<Post> postList;

            if (jsonFile.exists()) {
                InputStream inputStreamJson = openFileInput(FILE_NAME);
                Type listType = new TypeToken<List<Post>>() {}.getType();
                postList = gson.fromJson(new java.util.Scanner(inputStreamJson).useDelimiter("\\A").next(), listType);
                inputStreamJson.close();
            } else {
                postList = new ArrayList<>();
            }

            // Crear un nuevo post con el orden correcto de los parámetros
            int newId = postList.isEmpty() ? 1 : postList.get(postList.size() - 1).getId() + 1;
            Post newPost = new Post(newId, username, imageFileName, caption); // Aquí el orden es el correcto: id, username, image, caption
            postList.add(newPost);

            // Guardar los datos en el archivo JSON
            try (FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE)) {
                String json = gson.toJson(postList);
                fos.write(json.getBytes());
            }

            Toast.makeText(this, "Post guardado", Toast.LENGTH_SHORT).show();

            // Regresar a la actividad principal (InstagramPrincipalActivity)
            Intent intent = new Intent(PostCreationActivity.this, InstagramPrincipal.class);
            startActivity(intent);
            finish();  // Finalizar esta actividad para evitar que el usuario regrese a esta pantalla al presionar el botón de atrás
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al guardar el post", Toast.LENGTH_SHORT).show();
        }
    }

}
