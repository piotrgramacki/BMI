package com.example.piotrek.bmi238493;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;

public class ResultActivity extends AppCompatActivity {
    private TextView bmi_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle extras = getIntent().getExtras();
        double bmi_value = 0.0;
        int cat_color = 0;
        if(extras != null) {
            bmi_value = extras.getDouble("bmi");
            cat_color = extras.getInt("color");
        }

        ConstraintLayout container = findViewById(R.id.container);

        bmi_data = findViewById(R.id.bmi_data);
        bmi_data.setText(String.format(Locale.getDefault(), "%.2f", bmi_value));
        container.setBackgroundColor(cat_color);

    }
}
