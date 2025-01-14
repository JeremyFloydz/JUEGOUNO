package com.example.unaclasel;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private String selectedImage = null; // Imagen seleccionada
    private String selectedWord = null;  // Palabra seleccionada
    private int correctMatchesFirstSet = 0; // Contador de emparejamientos correctos del primer conjunto
    private int correctMatchesSecondSet = 0; // Contador de emparejamientos correctos del segundo conjunto

    // Declaración de las imágenes de los dos conjuntos
    private ImageView imgCat, imgDog, imgElephant, imgBlueberry, imgApple, imgGrape, imgStrawberry;
    private TextView messageText; // TextView para los mensajes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización del primer conjunto de imágenes
        imgCat = findViewById(R.id.img_cat);
        imgDog = findViewById(R.id.img_dog);
        imgElephant = findViewById(R.id.img_elephant);

        // Inicialización del primer conjunto de palabras (TextView en lugar de Button)
        TextView btnCat = findViewById(R.id.btn_cat);
        TextView btnDog = findViewById(R.id.btn_dog);
        TextView btnElephant = findViewById(R.id.btn_elephant);

        // Inicialización del segundo conjunto de imágenes
        imgBlueberry = findViewById(R.id.img_blueberry);
        imgApple = findViewById(R.id.img_apple);
        imgGrape = findViewById(R.id.img_grape);
        imgStrawberry = findViewById(R.id.img_strawberry);

        // Inicialización del segundo conjunto de palabras (TextView en lugar de Button)
        TextView btnBlueberry = findViewById(R.id.btn_blueberry);
        TextView btnApple = findViewById(R.id.btn_apple);
        TextView btnGrape = findViewById(R.id.btn_grape);
        TextView btnStrawberry = findViewById(R.id.btn_strawberry);

        // Botón de comprobación
        TextView btnCheck = findViewById(R.id.btn_check);

        // Inicialización del TextView para los mensajes
        messageText = findViewById(R.id.message_text);
        messageText.setVisibility(View.INVISIBLE); // Ocultar el mensaje inicialmente

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
                showMessage("Selecciona una imagen y una palabra", Color.RED);
            } else if (selectedImage.equals(selectedWord)) {
                String[] messages = {"¡Muy bien!", "¡Enhorabuena!", "¡Excelente!"};
                int randomMessageIndex = (int) (Math.random() * messages.length);
                showMessage(messages[randomMessageIndex], Color.RED);

                // Cambiar color a rojo claro para las imágenes y botones emparejados
                changeMatchedColor(selectedImage, imgCat, imgDog, imgElephant, btnCat, btnDog, btnElephant,
                        imgBlueberry, imgApple, imgGrape, imgStrawberry, btnBlueberry, btnApple, btnGrape, btnStrawberry);

                // Eliminar el borde de las imágenes y botones emparejados
                if (selectedImage.equals("Gato")) {
                    removeBorder(imgCat, btnCat);
                } else if (selectedImage.equals("Perro")) {
                    removeBorder(imgDog, btnDog);
                } else if (selectedImage.equals("Elefante")) {
                    removeBorder(imgElephant, btnElephant);
                } else if (selectedImage.equals("Blueberry")) {
                    removeBorder(imgBlueberry, btnBlueberry);
                } else if (selectedImage.equals("Apple")) {
                    removeBorder(imgApple, btnApple);
                } else if (selectedImage.equals("Grape")) {
                    removeBorder(imgGrape, btnGrape);
                } else if (selectedImage.equals("Strawberry")) {
                    removeBorder(imgStrawberry, btnStrawberry);
                }

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
                    showMessage("¡Has ganado!", Color.RED);
                }

                // Reiniciar selección
                selectedImage = null;
                selectedWord = null;
            } else {
                showMessage("Incorrecto, vuelve a intentarlo", Color.RED);
            }
        });
    }

    private void showMessage(String message, int color) {
        messageText.setText(message);
        messageText.setTextColor(color);
        messageText.setVisibility(View.VISIBLE);

        // Animación para hacer que el mensaje aparezca de manera animada
        ObjectAnimator animator = ObjectAnimator.ofFloat(messageText, "alpha", 0f, 1f);
        animator.setDuration(500); // Duración de la animación
        animator.start();
    }

    private void highlightSelection(View selected, View... others) {
        // Si es un TextView, aplicar el borde
        if (selected instanceof TextView) {
            selected.setBackgroundResource(R.drawable.border); // Añadir el borde al TextView
        } else if (selected instanceof ImageView) {
            selected.setBackgroundResource(R.drawable.border); // Añadir el borde a la imagen
        }

        // Eliminar el borde de los otros TextViews
        for (View other : others) {
            if (other instanceof TextView) {
                other.setBackgroundResource(0); // Eliminar el borde del TextView
            } else if (other instanceof ImageView) {
                other.setBackgroundResource(0); // Eliminar el borde de la imagen
            }
        }
    }

    private void removeBorder(View... views) {
        for (View view : views) {
            view.setBackgroundResource(0); // Elimina el borde
        }
    }

    private boolean isFirstSet(String selectedImage) {
        return selectedImage.equals("Gato") || selectedImage.equals("Perro") || selectedImage.equals("Elefante");
    }

    private void changeMatchedColor(String match, ImageView imgCat, ImageView imgDog, ImageView imgElephant,
                                    TextView btnCat, TextView btnDog, TextView btnElephant,
                                    ImageView imgBlueberry, ImageView imgApple, ImageView imgGrape, ImageView imgStrawberry,
                                    TextView btnBlueberry, TextView btnApple, TextView btnGrape, TextView btnStrawberry) {
        switch (match) {
            case "Gato":
                imgCat.setEnabled(false);  // Deshabilitar la imagen
                btnCat.setEnabled(false);  // Deshabilitar el TextView
                imgCat.setAlpha(0.5f);  // Mantener la imagen en gris (opacidad reducida)
                btnCat.setAlpha(0.5f);  // Mantener el TextView en gris (opacidad reducida)
                break;
            case "Perro":
                imgDog.setEnabled(false);  // Deshabilitar la imagen
                btnDog.setEnabled(false);  // Deshabilitar el TextView
                imgDog.setAlpha(0.5f);  // Mantener la imagen en gris (opacidad reducida)
                btnDog.setAlpha(0.5f);  // Mantener el TextView en gris (opacidad reducida)
                break;
            case "Elefante":
                imgElephant.setEnabled(false);  // Deshabilitar la imagen
                btnElephant.setEnabled(false);  // Deshabilitar el TextView
                imgElephant.setAlpha(0.5f);  // Mantener la imagen en gris (opacidad reducida)
                btnElephant.setAlpha(0.5f);  // Mantener el TextView en gris (opacidad reducida)
                break;
            case "Blueberry":
                imgBlueberry.setEnabled(false);  // Deshabilitar la imagen
                btnBlueberry.setEnabled(false);  // Deshabilitar el TextView
                imgBlueberry.setAlpha(0.5f);  // Mantener la imagen en gris (opacidad reducida)
                btnBlueberry.setAlpha(0.5f);  // Mantener el TextView en gris (opacidad reducida)
                break;
            case "Apple":
                imgApple.setEnabled(false);  // Deshabilitar la imagen
                btnApple.setEnabled(false);  // Deshabilitar el TextView
                imgApple.setAlpha(0.5f);  // Mantener la imagen en gris (opacidad reducida)
                btnApple.setAlpha(0.5f);  // Mantener el TextView en gris (opacidad reducida)
                break;
            case "Grape":
                imgGrape.setEnabled(false);  // Deshabilitar la imagen
                btnGrape.setEnabled(false);  // Deshabilitar el TextView
                imgGrape.setAlpha(0.5f);  // Mantener la imagen en gris (opacidad reducida)
                btnGrape.setAlpha(0.5f);  // Mantener el TextView en gris (opacidad reducida)
                break;
            case "Strawberry":
                imgStrawberry.setEnabled(false);  // Deshabilitar la imagen
                btnStrawberry.setEnabled(false);  // Deshabilitar el TextView
                imgStrawberry.setAlpha(0.5f);  // Mantener la imagen en gris (opacidad reducida)
                btnStrawberry.setAlpha(0.5f);  // Mantener el TextView en gris (opacidad reducida)
                break;
        }
    }

    private void toggleFirstSet(boolean show, ImageView imgCat, ImageView imgDog, ImageView imgElephant,
                                TextView btnCat, TextView btnDog, TextView btnElephant) {
        int visibility = show ? View.VISIBLE : View.GONE;
        imgCat.setVisibility(visibility);
        imgDog.setVisibility(visibility);
        imgElephant.setVisibility(visibility);
        btnCat.setVisibility(visibility);
        btnDog.setVisibility(visibility);
        btnElephant.setVisibility(visibility);
    }

    private void toggleSecondSet(boolean show, ImageView imgBlueberry, ImageView imgApple, ImageView imgGrape, ImageView imgStrawberry,
                                 TextView btnBlueberry, TextView btnApple, TextView btnGrape, TextView btnStrawberry) {
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
