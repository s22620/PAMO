package com.example.bmicalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Główna aktywność aplikacji do obliczania wskaźnika BMI (Body Mass Index).
 * Pozwala użytkownikowi wprowadzić wagę i wzrost, a następnie oblicza BMI
 * i wyświetla odpowiednią kategorię.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Włączenie trybu Edge-to-Edge dla nowoczesnego wyglądu UI
        EdgeToEdge.enable(this);
        // Ustawienie układu z pliku XML
        setContentView(R.layout.activity_main);
        // Konfiguracja obsługi wcięć okna dla zapewnienia prawidłowego wyświetlania
        // na różnych urządzeniach, uwzględniając paski systemowe
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicjalizacja elementów interfejsu użytkownika
        Button myButton = findViewById(R.id.button);
        EditText heightInput = findViewById(R.id.heightInput);
        EditText weightInput = findViewById(R.id.weightInput);
        TextView resultNumber = findViewById(R.id.resultNumber);
        TextView resultText = findViewById(R.id.resultText);

        // Ustawienie nasłuchiwania kliknięć przycisku
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pobranie wartości wprowadzonych przez użytkownika
                String heightValue = heightInput.getText().toString();
                String weightValue = weightInput.getText().toString();

                // Sprawdzenie, czy pola nie są puste
                if (heightValue.isEmpty() || weightValue.isEmpty()) {
                    // Wyświetlenie komunikatu o błędzie, jeśli którekolwiek pole jest puste
                    Toast.makeText(MainActivity.this, "Please enter both height and weight", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Konwersja wartości tekstowych na liczby
                double heightCm = Double.parseDouble(heightValue);
                double weight = Double.parseDouble(weightValue);
                // Konwersja wzrostu z centymetrów na metry
                double heightM = heightCm / 100;
                // Obliczenie BMI według wzoru: waga / (wzrost^2)
                double bmi = weight / (heightM * heightM);

                // Formatowanie wyniku BMI do dwóch miejsc po przecinku
                String formattedBmi = String.format("%.2f", bmi);

                // Wyświetlenie obliczonego BMI
                resultNumber.setText("BMI: " + formattedBmi);
                // Wyświetlenie kategorii BMI
                resultText.setText(getBMICategory(bmi));
            }
        });
    }

    /**
     * Metoda określająca kategorię BMI na podstawie wartości liczbowej.
     *
     * @param bmi Wartość wskaźnika BMI
     * @return String z nazwą kategorii BMI
     */
    public static String getBMICategory(double bmi) {
        if (bmi < 18.5) return "Niedowaga";
        if (bmi < 25) return "Waga prawidłowa";
        if (bmi < 30) return "Nadwaga";
        return "Otyłość";
    }
}