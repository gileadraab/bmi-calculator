# BMI Calculator

An Android app for calculating Body Mass Index with Material Design UI.

## Features

- Calculate BMI from weight (kg) and height (cm or m)
- Toggle between centimeters and meters
- Input validation
- Health category classification with color-coded badges
- Material Design interface

## Requirements

- Android Studio Hedgehog (2023.1.1) or later
- Android SDK 24+
- Java 8+
- Gradle 8.8.0

## Setup

1. Open the project in Android Studio
2. Wait for Gradle sync to complete
3. Run on device or emulator

## Build

```bash
./gradlew build
```

## Run

Click the Run button in Android Studio or:

```bash
./gradlew installDebug
```

## Project Structure

```
app/src/main/
├── java/com/example/bmicalculator/
│   ├── MainActivity.java       # UI and event handling
│   ├── BMICalculator.java      # BMI calculation logic
│   ├── BMIResult.java          # Result data model
│   └── InputValidator.java     # Input validation
└── res/
    ├── layout/activity_main.xml
    ├── values/
    └── drawable/
```

## BMI Categories

| BMI | Category |
|-----|----------|
| < 18.5 | Underweight |
| 18.5 - 24.9 | Normal Weight |
| 25.0 - 29.9 | Overweight |
| 30.0 - 34.9 | Obesity Class I |
| 35.0 - 39.9 | Obesity Class II |
| ≥ 40.0 | Obesity Class III |

## Dependencies

- AndroidX AppCompat 1.6.1
- Material Components 1.11.0
- ConstraintLayout 2.1.4
- CardView 1.0.0

## License

Open source for educational purposes.
