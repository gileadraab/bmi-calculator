package com.example.bmicalculator;

import android.content.Context;
import androidx.core.content.ContextCompat;

public class BMICalculator {
    private final Context context;

    public BMICalculator(Context context) {
        this.context = context;
    }

    public BMIResult calculate(double weight, double heightInMeters) {
        double bmi = weight / (heightInMeters * heightInMeters);
        double roundedBMI = Math.round(bmi * 10.0) / 10.0;

        return new BMIResult(
            roundedBMI,
            getCategory(bmi),
            getCategoryColor(bmi),
            getCategoryDescription(bmi)
        );
    }

    private String getCategory(double bmi) {
        if (bmi < 18.5) {
            return context.getString(R.string.underweight);
        } else if (bmi >= 18.5 && bmi <= 24.9) {
            return context.getString(R.string.normal_weight);
        } else if (bmi >= 25.0 && bmi <= 29.9) {
            return context.getString(R.string.overweight);
        } else if (bmi >= 30.0 && bmi <= 34.9) {
            return context.getString(R.string.obesity_class_1);
        } else if (bmi >= 35.0 && bmi <= 39.9) {
            return context.getString(R.string.obesity_class_2);
        } else {
            return context.getString(R.string.obesity_class_3);
        }
    }

    private int getCategoryColor(double bmi) {
        if (bmi < 18.5) {
            return ContextCompat.getColor(context, R.color.category_underweight);
        } else if (bmi >= 18.5 && bmi <= 24.9) {
            return ContextCompat.getColor(context, R.color.category_normal);
        } else if (bmi >= 25.0 && bmi <= 29.9) {
            return ContextCompat.getColor(context, R.color.category_overweight);
        } else if (bmi >= 30.0 && bmi <= 34.9) {
            return ContextCompat.getColor(context, R.color.category_obesity_1);
        } else if (bmi >= 35.0 && bmi <= 39.9) {
            return ContextCompat.getColor(context, R.color.category_obesity_2);
        } else {
            return ContextCompat.getColor(context, R.color.category_obesity_3);
        }
    }

    private String getCategoryDescription(double bmi) {
        if (bmi < 18.5) {
            return context.getString(R.string.bmi_less_than_18_5);
        } else if (bmi >= 18.5 && bmi <= 24.9) {
            return context.getString(R.string.bmi_18_5_24_9);
        } else if (bmi >= 25.0 && bmi <= 29.9) {
            return context.getString(R.string.bmi_25_0_29_9);
        } else if (bmi >= 30.0 && bmi <= 34.9) {
            return context.getString(R.string.bmi_30_0_34_9);
        } else if (bmi >= 35.0 && bmi <= 39.9) {
            return context.getString(R.string.bmi_35_0_39_9);
        } else {
            return context.getString(R.string.bmi_40_and_above);
        }
    }
}
