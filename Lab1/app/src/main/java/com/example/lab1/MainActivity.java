package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addBtn = findViewById(R.id.btn_add);

        // addBtn.setOnClickListener(new View.OnClickListener() {
        addBtn.setOnClickListener(v -> {
            Addition add = new Addition();

                EditText firstNum = findViewById(R.id.first_num);
                EditText secondNum = findViewById(R.id.second_num);

                int fNum = Integer.parseInt(firstNum.getText().toString());
                int sNum = Integer.parseInt(secondNum.getText().toString());

//            int fNum = Integer.parseInt(((EditText)findViewById(R.id.first_num)).getText().toString());
//            int sNum = Integer.parseInt(((EditText)findViewById(R.id.second_num)).getText().toString());

            add.setFirstNum(fNum);
            add.setSecondNum(sNum);
            int sum = add.add();

            EditText addTxt = findViewById(R.id.sum);
            addTxt.setText(String.valueOf(sum));
        });
    }
}