# Student stress level predictor

Predict student stress levels with a personalized approach! Using TensorFlow and machine learning, this app estimates stress based on student responses.

## ⭐️Features
### Questions form
- **Personalized Questions:** Student answers a series of well-being and activity questions on a scale from 1 to 5.
- **Real-time Stress Prediction:** After completing the questions, the app generates a stress prediction using a machine learning model.

## ⚙️Technologies
### 📱App:
- **TensorFlow Lite:** Imports and runs the TensorFlow model directly on the device for fast predictions.
- **Jetpack Compose:** Provides a modern UI for a smooth user experience.
  
### 🧠 Model:
- **TensorFlow:** The model, trained using kernel methods, was built in Python to predict stress.
- **Dataset:** The dataset includes various student features related to well-being and was processed for model training in Python.

### 📊 Dataset
- **Source:** The dataset was obtained from [Kaggle](https://www.kaggle.com/datasets/samyakb/student-stress-factors), which includes various features reflecting student well-being and stress factors.
- **Features:** The dataset includes critical indicators like `sleepQuality`, `headacheFrequency`, `academicPerformance`, `studyLoad`, and `extracurricularActivities`. These features help the model evaluate the primary factors contributing to stress.

## Installation

1. Clone the repository:
```bash
git clone https://github.com/AdamDawi/Student-stress-level-predictor
```
2. Open the project in Android Studio.
3. Make sure to sync project dependencies and verify TensorFlow Lite model setup.

## Here are some overview pictures:
![11](https://github.com/user-attachments/assets/8a207464-34b5-4774-97d7-57db67fbcf34)
![22](https://github.com/user-attachments/assets/74176846-ad28-4d08-8c74-182d3a1676c9)

## Requirements
Minimum version: Android 8 (API level 26) or later📱

Target version: Android 14 (API level 34) or later📱

## Author

Adam Dawidziuk🧑‍💻
