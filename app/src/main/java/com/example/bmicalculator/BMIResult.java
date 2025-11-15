package com.example.bmicalculator;

public class BMIResult {
    private final double value;
    private final String category;
    private final int color;
    private final String description;

    public BMIResult(double value, String category, int color, String description) {
        this.value = value;
        this.category = category;
        this.color = color;
        this.description = description;
    }

    public double getValue() {
        return value;
    }

    public String getCategory() {
        return category;
    }

    public int getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }
}
