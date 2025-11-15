package com.example.bmicalculator;

public class InputValidator {

    public static class ValidationResult {
        private final boolean isValid;
        private final String errorMessage;

        public ValidationResult(boolean isValid, String errorMessage) {
            this.isValid = isValid;
            this.errorMessage = errorMessage;
        }

        public boolean isValid() {
            return isValid;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

    public static ValidationResult validateWeight(String weightStr) {
        if (weightStr == null || weightStr.trim().isEmpty()) {
            return new ValidationResult(false, "Please enter your weight");
        }

        try {
            double weight = Double.parseDouble(weightStr);

            if (weight <= 0) {
                return new ValidationResult(false, "Weight must be a positive value");
            }

            if (weight > 1000) {
                return new ValidationResult(false, "Please enter a realistic weight value");
            }

            return new ValidationResult(true, null);
        } catch (NumberFormatException e) {
            return new ValidationResult(false, "Please enter a valid numeric value");
        }
    }

    public static ValidationResult validateHeight(String heightStr, boolean isCentimeters) {
        if (heightStr == null || heightStr.trim().isEmpty()) {
            return new ValidationResult(false, "Please enter your height");
        }

        try {
            double height = Double.parseDouble(heightStr);

            if (height <= 0) {
                return new ValidationResult(false, "Height must be a positive value");
            }

            if (isCentimeters) {
                if (height < 50 || height > 300) {
                    return new ValidationResult(false, "Please enter a realistic height (50-300 cm)");
                }
            } else {
                if (height < 0.5 || height > 3.0) {
                    return new ValidationResult(false, "Please enter a realistic height (0.5-3.0 m)");
                }
            }

            return new ValidationResult(true, null);
        } catch (NumberFormatException e) {
            return new ValidationResult(false, "Please enter a valid numeric value");
        }
    }
}
