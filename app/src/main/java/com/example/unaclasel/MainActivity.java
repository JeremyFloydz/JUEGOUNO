package com.example.unaclasel;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private String selectedImage = null; // Imagen seleccionada
    private String selectedWord = null;  // Palabra seleccionada
    private int correctMatchesFirstSet = 0; // Contador de emparejamientos correctos del primer conjunto
    private int correctMatchesSecondSet = 0; // Contador de emparejamientos correctos del segundo conjunto

    // Declaración de las imágenes de los dos conjuntos
    private ImageView imgCat, imgDog, imgElephant, imgBlueberry, imgApple, imgGrape, imgStrawberry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización del primer conjunto de imágenes
        imgCat = findViewById(R.id.img_cat);
        imgDog = findViewById(R.id.img_dog);
        imgElephant = findViewById(R.id.img_elephant);

        // Inicialización del primer conjunto de palabras
        Button btnCat = findViewById(R.id.btn_cat);
        Button btnDog = findViewById(R.id.btn_dog);
        Button btnElephant = findViewById(R.id.btn_elephant);

        // Inicialización del segundo conjunto de imágenes
        imgBlueberry = findViewById(R.id.img_blueberry);
        imgApple = findViewById(R.id.img_apple);
        imgGrape = findViewById(R.id.img_grape);
        imgStrawberry = findViewById(R.id.img_strawberry);

        // Inicialización del segundo conjunto de palabras
        Button btnBlueberry = findViewById(R.id.btn_blueberry);
        Button btnApple = findViewById(R.id.btn_apple);
        Button btnGrape = findViewById(R.id.btn_grape);
        Button btnStrawberry = findViewById(R.id.btn_strawberry);

        // Botón de comprobación
        Button btnCheck = findViewById(R.id.btn_check);

        // Ocultar el segundo conjunto inicialmente
        toggleSecondSet(false, imgBlueberry, imgApple, imgGrape, imgStrawberry, btnBlueberry, btnApple, btnGrape, btnStrawberry);

        // Selección del primer conjunto de imágenes
        imgCat.setOnClickListener(view -> {
            selectedImage = "Gato";
            highlightSelection(imgCat, imgDog, imgElephant);
        });

        imgDog.setOnClickListener(view -> {
            selectedImage = "Perro";
            highlightSelection(imgDog, imgCat, imgElephant);
        });

        imgElephant.setOnClickListener(view -> {
            selectedImage = "Elefante";
            highlightSelection(imgElephant, imgCat, imgDog);
        });

        // Selección del primer conjunto de palabras
        btnCat.setOnClickListener(view -> {
            selectedWord = "Gato";
            highlightSelection(btnCat, btnDog, btnElephant);
        });

        btnDog.setOnClickListener(view -> {
            selectedWord = "Perro";
            highlightSelection(btnDog, btnCat, btnElephant);
        });

        btnElephant.setOnClickListener(view -> {
            selectedWord = "Elefante";
            highlightSelection(btnElephant, btnCat, btnDog);
        });

        // Selección del segundo conjunto de imágenes
        imgBlueberry.setOnClickListener(view -> {
            selectedImage = "Blueberry";
            highlightSelection(imgBlueberry, imgApple, imgGrape, imgStrawberry);
        });

        imgApple.setOnClickListener(view -> {
            selectedImage = "Apple";
            highlightSelection(imgApple, imgBlueberry, imgGrape, imgStrawberry);
        });

        imgGrape.setOnClickListener(view -> {
            selectedImage = "Grape";
            highlightSelection(imgGrape, imgBlueberry, imgApple, imgStrawberry);
        });

        imgStrawberry.setOnClickListener(view -> {
            selectedImage = "Strawberry";
            highlightSelection(imgStrawberry, imgBlueberry, imgApple, imgGrape);
        });

        // Selección del segundo conjunto de palabras
        btnBlueberry.setOnClickListener(view -> {
            selectedWord = "Blueberry";
            highlightSelection(btnBlueberry, btnApple, btnGrape, btnStrawberry);
        });

        btnApple.setOnClickListener(view -> {
            selectedWord = "Apple";
            highlightSelection(btnApple, btnBlueberry, btnGrape, btnStrawberry);
        });

        btnGrape.setOnClickListener(view -> {
            selectedWord = "Grape";
            highlightSelection(btnGrape, btnBlueberry, btnApple, btnStrawberry);
        });

        btnStrawberry.setOnClickListener(view -> {
            selectedWord = "Strawberry";
            highlightSelection(btnStrawberry, btnBlueberry, btnApple, btnGrape);
        });

        // Comprobación
        btnCheck.setOnClickListener(view -> {
            if (selectedImage == null || selectedWord == null) {
                Toast.makeText(MainActivity.this, "Selecciona una imagen y una palabra", Toast.LENGTH_SHORT).show();
            } else if (selectedImage.equals(selectedWord)) {
                Toast.makeText(MainActivity.this, "¡Correcto! " + selectedImage + " coincide con " + selectedWord, Toast.LENGTH_SHORT).show();

                // Cambiar color a rojo claro para las imágenes y botones emparejados
                changeMatchedColor(selectedImage, imgCat, imgDog, imgElephant, btnCat, btnDog, btnElephant,
                        imgBlueberry, imgApple, imgGrape, imgStrawberry, btnBlueberry, btnApple, btnGrape, btnStrawberry);

                // Actualizar los contadores de emparejamientos
                if (isFirstSet(selectedImage)) {
                    correctMatchesFirstSet++;
                } else {
                    correctMatchesSecondSet++;
                }

                // Mostrar el segundo conjunto después de 3 emparejamientos correctos del primer conjunto
                if (correctMatchesFirstSet == 3) {
                    toggleFirstSet(false, imgCat, imgDog, imgElephant, btnCat, btnDog, btnElephant);
                    toggleSecondSet(true, imgBlueberry, imgApple, imgGrape, imgStrawberry, btnBlueberry, btnApple, btnGrape, btnStrawberry);
                }

                // Verificar si todos los emparejamientos se han hecho correctamente
                if (correctMatchesFirstSet == 3 && correctMatchesSecondSet == 4) {
                    Toast.makeText(MainActivity.this, "¡Felicidades! Has emparejado todas las imágenes correctamente.", Toast.LENGTH_LONG).show();
                    // Aquí puedes agregar lógica para finalizar el juego o mostrar un mensaje de victoria
                }

                // Reiniciar selección
                selectedImage = null;
                selectedWord = null;
            } else {
                Toast.makeText(MainActivity.this, "¡Incorrecto! " + selectedImage + " no coincide con " + selectedWord, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void highlightSelection(View selected, View... others) {
        selected.setAlpha(1.0f);
        for (View other : others) {
            other.setAlpha(0.5f);
        }
    }

    private boolean isFirstSet(String selectedImage) {
        return selectedImage.equals("Gato") || selectedImage.equals("Perro") || selectedImage.equals("Elefante");
    }

    private void changeMatchedColor(String match, ImageView imgCat, ImageView imgDog, ImageView imgElephant,
                                    Button btnCat, Button btnDog, Button btnElephant,
                                    ImageView imgBlueberry, ImageView imgApple, ImageView imgGrape, ImageView imgStrawberry,
                                    Button btnBlueberry, Button btnApple, Button btnGrape, Button btnStrawberry) {
        int color = Color.parseColor("#FFCCCB"); // Rojo claro
        switch (match) {
            case "Gato":
                imgCat.setBackgroundColor(color);
                btnCat.setBackgroundColor(color);
                imgCat.setEnabled(false);  // Deshabilitar la imagen
                btnCat.setEnabled(false);  // Deshabilitar el botón
                imgCat.setAlpha(0.5f); // Mantener la opacidad baja para mostrar que está deshabilitado
                btnCat.setAlpha(0.5f);  // Mantener la opacidad baja para mostrar que está deshabilitado
                break;
            case "Perro":
                imgDog.setBackgroundColor(color);
                btnDog.setBackgroundColor(color);
                imgDog.setEnabled(false);  // Deshabilitar la imagen
                btnDog.setEnabled(false);  // Deshabilitar el botón
                imgDog.setAlpha(0.5f); // Mantener la opacidad baja para mostrar que está deshabilitado
                btnDog.setAlpha(0.5f);  // Mantener la opacidad baja para mostrar que está deshabilitado
                break;
            case "Elefante":
                imgElephant.setBackgroundColor(color);
                btnElephant.setBackgroundColor(color);
                imgElephant.setEnabled(false);  // Deshabilitar la imagen
                btnElephant.setEnabled(false);  // Deshabilitar el botón
                imgElephant.setAlpha(0.5f); // Mantener la opacidad baja para mostrar que está deshabilitado
                btnElephant.setAlpha(0.5f);  // Mantener la opacidad baja para mostrar que está deshabilitado
                break;
            case "Blueberry":
                imgBlueberry.setBackgroundColor(color);
                btnBlueberry.setBackgroundColor(color);
                imgBlueberry.setEnabled(false);  // Deshabilitar la imagen
                btnBlueberry.setEnabled(false);  // Deshabilitar el botón
                imgBlueberry.setAlpha(0.5f); // Mantener la opacidad baja para mostrar que está deshabilitado
                btnBlueberry.setAlpha(0.5f);  // Mantener la opacidad baja para mostrar que está deshabilitado
                break;
            case "Apple":
                imgApple.setBackgroundColor(color);
                btnApple.setBackgroundColor(color);
                imgApple.setEnabled(false);  // Deshabilitar la imagen
                btnApple.setEnabled(false);  // Deshabilitar el botón
                imgApple.setAlpha(0.5f); // Mantener la opacidad baja para mostrar que está deshabilitado
                btnApple.setAlpha(0.5f);  // Mantener la opacidad baja para mostrar que está deshabilitado
                break;
            case "Grape":
                imgGrape.setBackgroundColor(color);
                btnGrape.setBackgroundColor(color);
                imgGrape.setEnabled(false);  // Deshabilitar la imagen
                btnGrape.setEnabled(false);  // Deshabilitar el botón
                imgGrape.setAlpha(0.5f); // Mantener la opacidad baja para mostrar que está deshabilitado
                btnGrape.setAlpha(0.5f);  // Mantener la opacidad baja para mostrar que está deshabilitado
                break;
            case "Strawberry":
                imgStrawberry.setBackgroundColor(color);
                btnStrawberry.setBackgroundColor(color);
                imgStrawberry.setEnabled(false);  // Deshabilitar la imagen
                btnStrawberry.setEnabled(false);  // Deshabilitar el botón
                imgStrawberry.setAlpha(0.5f); // Mantener la opacidad baja para mostrar que está deshabilitado
                btnStrawberry.setAlpha(0.5f);  // Mantener la opacidad baja para mostrar que está deshabilitado
                break;
        }
    }

    private void toggleFirstSet(boolean show, ImageView imgCat, ImageView imgDog, ImageView imgElephant,
                                Button btnCat, Button btnDog, Button btnElephant) {
        int visibility = show ? View.VISIBLE : View.GONE;
        imgCat.setVisibility(visibility);
        imgDog.setVisibility(visibility);
        imgElephant.setVisibility(visibility);
        btnCat.setVisibility(visibility);
        btnDog.setVisibility(visibility);
        btnElephant.setVisibility(visibility);
    }

    private void toggleSecondSet(boolean show, ImageView imgBlueberry, ImageView imgApple, ImageView imgGrape, ImageView imgStrawberry,
                                 Button btnBlueberry, Button btnApple, Button btnGrape, Button btnStrawberry) {
        int visibility = show ? View.VISIBLE : View.GONE;
        imgBlueberry.setVisibility(visibility);
        imgApple.setVisibility(visibility);
        imgGrape.setVisibility(visibility);
        imgStrawberry.setVisibility(visibility);
        btnBlueberry.setVisibility(visibility);
        btnApple.setVisibility(visibility);
        btnGrape.setVisibility(visibility);
        btnStrawberry.setVisibility(visibility);
    }
}
