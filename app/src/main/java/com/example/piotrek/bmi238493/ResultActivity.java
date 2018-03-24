package com.example.piotrek.bmi238493;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;

public class ResultActivity extends AppCompatActivity {
    double bmiValue;
    int catColor;
    ConstraintLayout container;
    TextView bmiData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getExtras();
        initializeViews();
        showResult();
    }

    private void getExtras() {
        Bundle extras = getIntent().getExtras();
        bmiValue = 0.0;
        catColor = 0;
        if(extras != null) {
            bmiValue = extras.getDouble("bmi");
            catColor = extras.getInt("color");
        }
    }

    private void initializeViews() {
        container = findViewById(R.id.container);
        bmiData = findViewById(R.id.bmi_data);
    }

    private void showResult() {
        bmiData.setText(String.format(Locale.getDefault(), "%.2f", bmiValue));
        container.setBackgroundColor(catColor);
    }
}
