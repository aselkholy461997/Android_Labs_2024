package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Practice 1

//        Button calcBtn = (Button) findViewById(R.id.btn_calculateTemp);
//        calcBtn.setOnClickListener(v -> {
//            Temperature currentTemperature = new Temperature();
//            EditText tempText = findViewById(R.id.txt_temp);
//            RadioButton celsiusRadio = findViewById(R.id.radio_celsius);
//            RadioButton fahrenheitRadio = findViewById(R.id.radio_fahrenheit);
//            try {
//                float temperature = Float.parseFloat(tempText.getText().toString());
//                currentTemperature.setTemperature(temperature);
//                if (celsiusRadio.isChecked())
//                    temperature = currentTemperature.convertFahrenheitToCelsius();
//
//                else if (fahrenheitRadio.isChecked())
//                    temperature = currentTemperature.convertCelsiusToFahrenheit();
//
//                tempText.setText(String.valueOf(temperature));
//            } catch (Exception e) {
//                Toast.makeText(MainActivity.this, "Please enter a valid number",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });

//        Practice 2

        final Spinner tempOptions = findViewById(R.id.temp_options);
        tempOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View selectedItemView,
                                       int position, long id) {
                Temperature currentTemperature = new Temperature();
                EditText tempText = findViewById(R.id.txt_temp);
                try {
                    float temperature = Float.parseFloat(tempText.getText().toString());
                    currentTemperature.setTemperature(temperature);
                    String selectedOption = tempOptions.getSelectedItem().toString();
                    if (selectedOption.equals("Celsius"))
                        temperature = currentTemperature.convertFahrenheitToCelsius();

                    else if (selectedOption.equals("Fahrenheit"))
                        temperature = currentTemperature.convertCelsiusToFahrenheit();

                    tempText.setText(String.valueOf(temperature));
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Please enter a valid number",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(MainActivity.this, "Please select an option",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}