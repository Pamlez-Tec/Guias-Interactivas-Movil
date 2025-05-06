package com.example.guiaprueba.instagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.guiaprueba.R;
import com.google.android.material.appbar.MaterialToolbar;
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
    private View guideOverlay;
    private TextView guideText;
    private ImageButton btnClose, btnBack, btnNext;
    private int currentStep = 0;
    private final String[] guideSteps = {
            "Estas a un paso de poder realizar tu publicación, para ello debes agregar un comentraio. " ,
            "Para finalizar debes darle al botón publicar, este publicará tu imagen y podrás verla en el menú principal."
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_creation);

        imagePreview = findViewById(R.id.imagePreview);
        captionInput = findViewById(R.id.captionInput);
        Button saveButton = findViewById(R.id.saveButton);
        showGuide();
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
        String username = "Tú"; // Valor fijo

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
