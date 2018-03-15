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
    private Switch units_switch;
    private TextView mass_text;
    private TextView height_text;
    private EditText mass_data;
    private EditText height_data;
    private BMI bmi_manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        units_switch = findViewById(R.id.switch_units);
        Button calc_button = findViewById(R.id.calc);
        mass_text = findViewById(R.id.mass_text);
        height_text = findViewById(R.id.height_text);
        mass_data = findViewById(R.id.mass_data);
        height_data = findViewById(R.id.height_data);

        restoreData();

        units_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!units_switch.isChecked()) {
                    changeToSI();
                } else {
                    changeToImperial();
                }
            }
        });

        final Intent result_intent = new Intent(this, ResultActivity.class);
        calc_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double mass = getDoubleFromField(mass_data);
                double height = getDoubleFromField(height_data);
                if (units_switch.isChecked())
                    bmi_manager = new BMIForImperial(mass, height);
                else bmi_manager = new BMIForKg(mass, height);

                try {
                    double res = bmi_manager.calculate();
                    result_intent.putExtra("bmi", res);
                    result_intent.putExtra("color", getCategoryColor(bmi_manager.category()));
                    startActivity(result_intent);
                } catch (IllegalArgumentException e) {
                    Toast.makeText(
                            getApplicationContext(),
                            getString(R.string.wrong_data),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
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


    private void changeToSI() {
        units_switch.setText(getString(R.string.units_si));
        mass_text.setText(getString(R.string.mass_si));
        height_text.setText(getString(R.string.height_si));
        mass_data.getText().clear();
        height_data.getText().clear();
    }

    private void changeToImperial() {
        units_switch.setText(getString(R.string.units_imperial));
        mass_text.setText(getString(R.string.mass_imp));
        height_text.setText(getString(R.string.height_imp));
        mass_data.getText().clear();
        height_data.getText().clear();
    }

    private double getDoubleFromField(EditText field) {
        String res = field.getText().toString();

        if (res.length() == 0)
            return 0.0;
        else return Double.valueOf(res);
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
        String massString = mass_data.getText().toString();
        String heightString = height_data.getText().toString();
        boolean isImperial = units_switch.isChecked();
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
                units_switch.setChecked(Boolean.parseBoolean(isImperial));
                mass_data.setText(massString);
                height_data.setText(heightString);
            }
            catch (IOException e) {
                Toast.makeText(getApplicationContext(), getString(R.string.file_error), Toast.LENGTH_LONG).show();
            }
        }
    }
}
