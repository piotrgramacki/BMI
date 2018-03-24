package com.example.piotrek.bmi238493;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Button calcButton;
    private Switch unitsSwitch;
    private TextView massText;
    private TextView heightText;
    private EditText massData;
    private EditText heightData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        restoreData();
        setListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                final Intent about_intent = new Intent(this, AboutActivity.class);
                startActivity(about_intent);
                return true;
            case R.id.save:
                saveData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initializeViews() {
        unitsSwitch = findViewById(R.id.switch_units);
        calcButton = findViewById(R.id.calc);
        massText = findViewById(R.id.mass_text);
        heightText = findViewById(R.id.height_text);
        massData = findViewById(R.id.mass_data);
        heightData = findViewById(R.id.height_data);
    }

    private void setListeners() {
        unitsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                changeUnits();
            }
        });

        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateBMI();
            }
        });
    }

    private void calculateBMI() {
        final Intent result_intent = new Intent(this, ResultActivity.class);

        double mass = getDoubleFromField(massData);
        double height = getDoubleFromField(heightData);
        BMI bmiManager;
        if (unitsSwitch.isChecked())
            bmiManager = new BMIForImperial(mass, height);
        else bmiManager = new BMIForKg(mass, height);

        try {
            double res = bmiManager.calculate();
            result_intent.putExtra("bmi", res);
            result_intent.putExtra("color", getCategoryColor(bmiManager.category()));
            startActivity(result_intent);
        } catch (IllegalArgumentException e) {
            Toast.makeText(
                    getApplicationContext(),
                    getString(R.string.wrong_data),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void changeUnits() {
        if (!unitsSwitch.isChecked()) {
            changeToSI();
        } else {
            changeToImperial();
        }
    }

    private void changeToSI() {
        unitsSwitch.setText(getString(R.string.units_si));
        massText.setText(getString(R.string.mass_si));
        heightText.setText(getString(R.string.height_si));
        massData.getText().clear();
        heightData.getText().clear();
    }

    private void changeToImperial() {
        unitsSwitch.setText(getString(R.string.units_imperial));
        massText.setText(getString(R.string.mass_imp));
        heightText.setText(getString(R.string.height_imp));
        massData.getText().clear();
        heightData.getText().clear();
    }

    private double getDoubleFromField(EditText field) {
        String res = field.getText().toString();
        double d_result = 0.0;
        if (res.length() != 0) {
            try {
                d_result = Double.valueOf(res);
            } catch (NumberFormatException e) {
                d_result = 0.0;
            }
        }

        return d_result;
    }

    private int getCategoryColor(BMICategory cat) {
        switch (cat) {
            case UNDERWEIGHT:
                return getResources().getColor(R.color.underweight);
            case NORMAL:
                return getResources().getColor(R.color.normal);
            case OVERWEIGHT:
                return getResources().getColor(R.color.overweight);
            case OBESITY:
                return getResources().getColor(R.color.obesity);
            default:
                return getResources().getColor(R.color.heavy_obesity);
        }
    }

    private void saveData() {
        String massString = massData.getText().toString();
        String heightString = heightData.getText().toString();
        boolean isImperial = unitsSwitch.isChecked();
        String dataToSave = isImperial + "\n" + massString + "\n" + heightString;
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = openFileOutput(getString(R.string.filename), Context.MODE_PRIVATE);
            fileOutputStream.write(dataToSave.getBytes());
            fileOutputStream.close();
            Toast.makeText(
                    getApplicationContext(),
                    getString(R.string.saved),
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(
                    getApplicationContext(),
                    getString(R.string.file_error),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void restoreData() {
        File directory = getApplicationContext().getFilesDir();
        File file = new File(directory, getString(R.string.filename));

        if(file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String isImperial = br.readLine();
                String massString = br.readLine();
                String heightString = br.readLine();
                unitsSwitch.setChecked(Boolean.parseBoolean(isImperial));
                massData.setText(massString);
                heightData.setText(heightString);
            }
            catch (IOException e) {
                Toast.makeText(getApplicationContext(), getString(R.string.file_error), Toast.LENGTH_LONG).show();
            }
        }
    }
}
