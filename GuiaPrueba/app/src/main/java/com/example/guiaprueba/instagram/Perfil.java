package com.example.guiaprueba.instagram;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.guiaprueba.R;

public class Perfil extends AppCompatActivity {

    private ImageButton moreButton;
    private View guideOverlay;
    private TextView guideText;
    private ImageButton btnClose, btnBack, btnNext;
    private int currentStep = 0;


    private final String[] guideSteps = {
            "Hay muchas ocasiones en que es necesario bloquear una persona por protección" +
                    ", ya que hay muchos usuarios con intenciones malignas en la aplicación. ",
            "Actualmente nos encontramos en el perfil del usuario, esta página " +
                    "tiene información básica del usuario, además es posible tomar acciones contra el usuario." +
                    " Para continuar presione los tres puntos junto al nombre del usuario"
    };
    private final String[] guideSteps1 = {
            "Hay tres tipos de acciones que se pueden realizar. La primera de ellas es restringir, " +
                    "permite ocultar todos los mensajes y publicaciones del usuario."+
            "La segunda es Bloquear, esta opción impide cualquier tipo de interacción con el usuario. La tercera es reportar, su funcionalidad es indicarle a Instagram las malas acciones de los usuarios para que tomen acciones, suele permitir bloquear al usuario.",
                    "Para finalizar el tutorial seleccione alguna de estas opciones"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        moreButton = findViewById(R.id.more_button);

        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
                currentStep = 0;
                showGuide2();
            }
        });
        showGuide();

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
    private void showGuide2() {
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

        updateStep1();

        btnClose.setOnClickListener(v -> guideOverlay.setVisibility(View.GONE));
        btnBack.setOnClickListener(v -> {
            if (currentStep > 0) {
                currentStep--;
                updateStep1();
            }
        });
        btnNext.setOnClickListener(v -> {
            if (currentStep < guideSteps1.length - 1) {
                currentStep++;
                updateStep1();
            }
        });

        guideOverlay.setVisibility(View.VISIBLE);
    }



    private void updateStep() {
        guideText.setText(guideSteps[currentStep]);
        btnBack.setVisibility(currentStep == 0 ? View.GONE : View.VISIBLE);
        btnNext.setVisibility(currentStep == guideSteps.length - 1 ? View.GONE : View.VISIBLE);
    }
    private void updateStep1() {
        guideText.setText(guideSteps1[currentStep]);
        btnBack.setVisibility(currentStep == 0 ? View.GONE : View.VISIBLE);
        btnNext.setVisibility(currentStep == guideSteps1.length - 1 ? View.GONE : View.VISIBLE);
    }
    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.profile_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_restrict) {
                    //Toast.makeText(Perfil.this, "Usuario restringido", Toast.LENGTH_SHORT).show();
                    finish();
                    return true;
                } else if (item.getItemId() == R.id.action_block) {
                    //Toast.makeText(Perfil.this, "Usuario bloqueado", Toast.LENGTH_SHORT).show();
                    finish();
                    return true;
                } else if (item.getItemId() == R.id.action_report) {
                    //Toast.makeText(Perfil.this, "Usuario reportado", Toast.LENGTH_SHORT).show();
                    finish();
                    return true;
                } else {
                    return false;
                }
            }
        });
        popup.show();
    }
}
