package com.example.guiaprueba.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.guiaprueba.R;

public class Menu extends AppCompatActivity {

    private Button btnPost;
    private Button btnBloquear;
    private Button btnOtros;
    private Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_instagram);

        // Inicializar las vistas
        initViews();

        // Configurar los listeners
        setupClickListeners();
    }

    private void initViews() {
        btnPost = findViewById(R.id.btnPost);
        btnBloquear = findViewById(R.id.btnBloquear);
        btnOtros = findViewById(R.id.btnDenunciar);
        btnVolver = findViewById(R.id.btnSalir);
    }

    private void setupClickListeners() {
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, InstagramPrincipal.class);
                startActivity(intent);
            }
        });

        btnBloquear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, Perfil.class);
                startActivity(intent);
            }
        });

        btnOtros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOtrosClick();
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleVolverClick();
            }
        });
    }

    private void handlePostClick() {
        Toast.makeText(this, "Realizar una publicación", Toast.LENGTH_SHORT).show();
        // Aquí puedes agregar la lógica para realizar una publicación
        // Intent intent = new Intent(this, PostActivity.class);
        // startActivity(intent);
    }

    private void handleBloquearClick() {
        Toast.makeText(this, "Bloquear a una persona", Toast.LENGTH_SHORT).show();
        // Aquí puedes agregar la lógica para bloquear personas
        // Intent intent = new Intent(this, BloquearActivity.class);
        // startActivity(intent);
    }

    private void handleOtrosClick() {
        Toast.makeText(this, "Otras opciones", Toast.LENGTH_SHORT).show();
        // Aquí puedes agregar la lógica para otras opciones
        // Intent intent = new Intent(this, OtrosActivity.class);
        // startActivity(intent);
    }

    private void handleVolverClick() {
        // Cerrar la actividad actual y volver a la anterior
        finish();
    }
}