package com.example.unaclasel;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String selectedImage = null; // Imagen seleccionada
    private String selectedWord = null;  // Palabra seleccionada
    private int correctMatchesFirstSet = 0; // Contador de emparejamientos correctos del primer conjunto
    private int correctMatchesSecondSet = 0; // Contador de emparejamientos correctos del segundo conjunto

    // Declaración de las imágenes de los dos conjuntos
    private ImageView imgCat, imgDog, imgElephant, imgBlueberry, imgApple, imgGrape, imgStrawberry;
    private TextView messageText; // TextView para los mensajes

    // Declaración de los TextView para las palabras
    private TextView btnCat, btnDog, btnElephant, btnBlueberry, btnApple, btnGrape, btnStrawberry;

    // Mapa de emparejamiento de imágenes con palabras
    private Map<String, String> imageWordMap = new HashMap<>();
    private Map<String, Boolean> matchedImages = new HashMap<>();
    private Map<String, Boolean> matchedWords = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización de vistas
        initViews();

        // Configuración del mapa de emparejamiento
        setupImageWordMap();

        // Inicialización del TextView para los mensajes
        messageText = findViewById(R.id.message_text);
        messageText.setVisibility(View.INVISIBLE); // Ocultar el mensaje inicialmente

        // Ocultar el segundo conjunto inicialmente
        toggleSecondSet(false);

        // Configuración de los listeners de imágenes y palabras
        setupListeners();
    }

    private void initViews() {
        // Inicialización de las imágenes
        imgCat = findViewById(R.id.img_cat);
        imgDog = findViewById(R.id.img_dog);
        imgElephant = findViewById(R.id.img_elephant);
        imgBlueberry = findViewById(R.id.img_blueberry);
        imgApple = findViewById(R.id.img_apple);
        imgGrape = findViewById(R.id.img_grape);
        imgStrawberry = findViewById(R.id.img_strawberry);

        // Inicialización de los TextViews para las palabras
        btnCat = findViewById(R.id.btn_cat);
        btnDog = findViewById(R.id.btn_dog);
        btnElephant = findViewById(R.id.btn_elephant);
        btnBlueberry = findViewById(R.id.btn_blueberry);
        btnApple = findViewById(R.id.btn_apple);
        btnGrape = findViewById(R.id.btn_grape);
        btnStrawberry = findViewById(R.id.btn_strawberry);
    }

    private void setupImageWordMap() {
        // Relación entre las imágenes y las palabras
        imageWordMap.put("Gato", "Elefante");
        imageWordMap.put("Perro", "Gato");
        imageWordMap.put("Elefante", "Perro");
        imageWordMap.put("Blueberry", "Grape");  // Blueberry hace match con Grape
        imageWordMap.put("Apple", "Apple");     // Apple hace match con Apple
        imageWordMap.put("Grape", "Strawberry"); // Grape hace match con Strawberry
        imageWordMap.put("Strawberry", "Blueberry"); // Strawberry hace match con Blueberry
    }

    private void setupListeners() {
        // Configuración de los listeners de las imágenes
        imgCat.setOnClickListener(view -> onImageSelected("Gato"));
        imgDog.setOnClickListener(view -> onImageSelected("Perro"));
        imgElephant.setOnClickListener(view -> onImageSelected("Elefante"));
        imgBlueberry.setOnClickListener(view -> onImageSelected("Blueberry"));
        imgApple.setOnClickListener(view -> onImageSelected("Apple"));
        imgGrape.setOnClickListener(view -> onImageSelected("Grape"));
        imgStrawberry.setOnClickListener(view -> onImageSelected("Strawberry"));

        // Configuración de los listeners de las palabras
        btnCat.setOnClickListener(view -> onWordSelected("Gato"));
        btnDog.setOnClickListener(view -> onWordSelected("Perro"));
        btnElephant.setOnClickListener(view -> onWordSelected("Elefante"));
        btnBlueberry.setOnClickListener(view -> onWordSelected("Blueberry"));
        btnApple.setOnClickListener(view -> onWordSelected("Apple"));
        btnGrape.setOnClickListener(view -> onWordSelected("Grape"));
        btnStrawberry.setOnClickListener(view -> onWordSelected("Strawberry"));

        // Comprobación
        TextView btnCheck = findViewById(R.id.btn_check);
        btnCheck.setOnClickListener(view -> checkMatch());
    }

    private void onImageSelected(String image) {
        selectedImage = image;
        highlightSelection(getImageView(image), getOtherImageViews(image));
    }

    private void onWordSelected(String word) {
        selectedWord = word;
        highlightSelection(getTextView(word), getOtherTextViews(word));
    }

    private void checkMatch() {
        if (selectedImage == null || selectedWord == null) {
            showMessage("Selecciona una imagen y una palabra", Color.RED);
        } else if (isCorrectMatch(selectedImage, selectedWord)) {
            showMessage("¡Muy bien!", Color.GREEN);

            // Actualizar los contadores de emparejamientos
            updateMatchCount(selectedImage);

            // Mostrar el segundo conjunto después de 3 emparejamientos correctos del primer conjunto
            if (correctMatchesFirstSet == 3) {
                toggleFirstSet(false);
                toggleSecondSet(true);
            }

            // Verificar si todos los emparejamientos se han hecho correctamente
            if (correctMatchesFirstSet == 3 && correctMatchesSecondSet == 4) {
                showMessage("¡Has ganado!", Color.GREEN);
            }

            // Reiniciar selección
            selectedImage = null;
            selectedWord = null;
        } else {
            showMessage("Incorrecto, vuelve a intentarlo", Color.RED);
        }
    }

    private void updateMatchCount(String selectedImage) {
        if (isFirstSet(selectedImage)) {
            correctMatchesFirstSet++;
        } else {
            correctMatchesSecondSet++;
        }

        // Cambiar color a rojo claro para las imágenes y botones emparejados
        changeMatchedColor(selectedImage);
        removeBorderForMatched(selectedImage);
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

    private boolean isCorrectMatch(String selectedImage, String selectedWord) {
        return imageWordMap.containsKey(selectedImage) && imageWordMap.get(selectedImage).equals(selectedWord);
    }

    private boolean isFirstSet(String selectedImage) {
        return selectedImage.equals("Gato") || selectedImage.equals("Perro") || selectedImage.equals("Elefante");
    }

    private void highlightSelection(View selected, View... others) {
        selected.setBackgroundResource(R.drawable.border);
        for (View other : others) {
            other.setBackgroundResource(0);
        }
    }

    private void removeBorderForMatched(String selectedImage) {
        if (selectedImage != null) {
            // Deshabilitar la imagen y ponerla gris
            View selectedView = getImageView(selectedImage);
            selectedView.setBackgroundResource(0);
            selectedView.setEnabled(false);
            selectedView.setAlpha(0.5f); // Hacer la imagen gris

            // Deshabilitar la palabra correspondiente y ponerla gris
            String correspondingWord = imageWordMap.get(selectedImage);
            View selectedTextView = getTextView(correspondingWord);
            selectedTextView.setBackgroundResource(0);
            selectedTextView.setEnabled(false);
            selectedTextView.setAlpha(0.5f); // Hacer la palabra gris

            // Marcar como emparejados
            matchedImages.put(selectedImage, true);
            matchedWords.put(correspondingWord, true);
        }
    }

    private void changeMatchedColor(String selectedImage) {
        if (matchedImages.getOrDefault(selectedImage, false)) {
            View selectedView = getImageView(selectedImage);
            View selectedTextView = getTextView(selectedImage);

            selectedView.setAlpha(0.5f);
            selectedTextView.setAlpha(0.5f);
        }
    }

    private View getImageView(String image) {
        switch (image) {
            case "Gato": return imgCat;
            case "Perro": return imgDog;
            case "Elefante": return imgElephant;
            case "Blueberry": return imgBlueberry;
            case "Apple": return imgApple;
            case "Grape": return imgGrape;
            case "Strawberry": return imgStrawberry;
            default: return null;
        }
    }

    private View getTextView(String word) {
        switch (word) {
            case "Gato": return btnCat;
            case "Perro": return btnDog;
            case "Elefante": return btnElephant;
            case "Blueberry": return btnBlueberry;
            case "Apple": return btnApple;
            case "Grape": return btnGrape;
            case "Strawberry": return btnStrawberry;
            default: return null;
        }
    }

    private View[] getOtherImageViews(String selectedImage) {
        switch (selectedImage) {
            case "Gato": return new View[]{imgDog, imgElephant};
            case "Perro": return new View[]{imgCat, imgElephant};
            case "Elefante": return new View[]{imgCat, imgDog};
            case "Blueberry": return new View[]{imgApple, imgGrape, imgStrawberry};
            case "Apple": return new View[]{imgBlueberry, imgGrape, imgStrawberry};
            case "Grape": return new View[]{imgBlueberry, imgApple, imgStrawberry};
            case "Strawberry": return new View[]{imgBlueberry, imgApple, imgGrape};
            default: return new View[]{};
        }
    }

    private View[] getOtherTextViews(String selectedWord) {
        switch (selectedWord) {
            case "Gato": return new View[]{btnDog, btnElephant};
            case "Perro": return new View[]{btnCat, btnElephant};
            case "Elefante": return new View[]{btnCat, btnDog};
            case "Blueberry": return new View[]{btnApple, btnGrape, btnStrawberry};
            case "Apple": return new View[]{btnBlueberry, btnGrape, btnStrawberry};
            case "Grape": return new View[]{btnBlueberry, btnApple, btnStrawberry};
            case "Strawberry": return new View[]{btnBlueberry, btnApple, btnGrape};
            default: return new View[]{};
        }
    }

    private void toggleFirstSet(boolean visible) {
        int visibility = visible ? View.VISIBLE : View.GONE;
        imgCat.setVisibility(visibility);
        imgDog.setVisibility(visibility);
        imgElephant.setVisibility(visibility);
        btnCat.setVisibility(visibility);
        btnDog.setVisibility(visibility);
        btnElephant.setVisibility(visibility);
    }

    private void toggleSecondSet(boolean visible) {
        int visibility = visible ? View.VISIBLE : View.GONE;
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
