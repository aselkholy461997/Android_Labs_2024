package com.example.lab2;

public class Temperature {
    private float temperature;

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    //Fahrenheit to Celsius
    public float convertFahrenheitToCelsius() {
        return ((temperature - 32) * 5/9);
    }

    //Celsius to Fahrenheit
    public float convertCelsiusToFahrenheit() {
        return ((temperature * 9)/5) + 32;
    }
}
