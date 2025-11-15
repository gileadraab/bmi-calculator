package com.example.bmicalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText weightInput;
    private TextInputEditText heightInput;
    private TextInputLayout heightInputLayout;
    private MaterialButton unitToggleButton;
    private MaterialButton calculateButton;
    private MaterialButton clearButton;

    private View resultDivider;
    private View resultLayout;
    private TextView bmiValue;
    private TextView bmiCategory;
    private TextView bmiDescription;

    private boolean isCentimeters = true;
    private BMICalculator bmiCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bmiCalculator = new BMICalculator(this);

        initializeViews();
        setupListeners();
    }

    private void initializeViews() {
        weightInput = findViewById(R.id.weightInput);
        heightInput = findViewById(R.id.heightInput);
        heightInputLayout = findViewById(R.id.heightInputLayout);
        unitToggleButton = findViewById(R.id.unitToggleButton);
        calculateButton = findViewById(R.id.calculateButton);
        clearButton = findViewById(R.id.clearButton);

        resultDivider = findViewById(R.id.resultDivider);
        resultLayout = findViewById(R.id.resultLayout);
        bmiValue = findViewById(R.id.bmiValue);
        bmiCategory = findViewById(R.id.bmiCategory);
        bmiDescription = findViewById(R.id.bmiDescription);
    }

    private void setupListeners() {
        calculateButton.setOnClickListener(v -> calculateBMI());
        clearButton.setOnClickListener(v -> clearInputs());
        unitToggleButton.setOnClickListener(v -> toggleHeightUnit());
    }

    private void calculateBMI() {
        String weightStr = weightInput.getText() != null ? weightInput.getText().toString() : "";
        String heightStr = heightInput.getText() != null ? heightInput.getText().toString() : "";

        InputValidator.ValidationResult weightValidation = InputValidator.validateWeight(weightStr);
        if (!weightValidation.isValid()) {
            showError(weightValidation.getErrorMessage());
            return;
        }

        InputValidator.ValidationResult heightValidation = InputValidator.validateHeight(heightStr, isCentimeters);
        if (!heightValidation.isValid()) {
            showError(heightValidation.getErrorMessage());
            return;
        }

        double weight = Double.parseDouble(weightStr);
        double height = Double.parseDouble(heightStr);

        if (isCentimeters) {
            height = height / 100.0;
        }

        BMIResult result = bmiCalculator.calculate(weight, height);
        displayResult(result);

        Snackbar.make(findViewById(android.R.id.content),
            getString(R.string.bmi_calculated, result.getValue(), result.getCategory()),
            Snackbar.LENGTH_SHORT).show();
    }

    private void displayResult(BMIResult result) {
        bmiValue.setText(String.format("%.1f", result.getValue()));
        bmiCategory.setText(result.getCategory());
        bmiCategory.setBackgroundColor(result.getColor());
        bmiDescription.setText(result.getDescription());

        resultDivider.setVisibility(View.VISIBLE);
        resultLayout.setVisibility(View.VISIBLE);
    }

    private void clearInputs() {
        weightInput.setText("");
        heightInput.setText("");

        resultDivider.setVisibility(View.GONE);
        resultLayout.setVisibility(View.GONE);

        Snackbar.make(findViewById(android.R.id.content),
            R.string.inputs_cleared,
            Snackbar.LENGTH_SHORT).show();
    }

    private void toggleHeightUnit() {
        isCentimeters = !isCentimeters;

        if (isCentimeters) {
            unitToggleButton.setText(R.string.unit_cm);
            heightInputLayout.setHint(R.string.height_cm);
        } else {
            unitToggleButton.setText(R.string.unit_m);
            heightInputLayout.setHint(R.string.height_m);
        }

        heightInput.setText("");
    }

    private void showError(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }
}
