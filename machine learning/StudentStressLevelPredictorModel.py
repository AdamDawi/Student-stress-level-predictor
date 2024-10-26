import pandas as pd
import numpy as np
from sklearn.preprocessing import StandardScaler
from sklearn.metrics import accuracy_score, confusion_matrix
from sklearn.model_selection import train_test_split
import tensorflow as tf

# Load the dataset
data = pd.read_csv('Student Stress Factors.csv', sep=',')
data.columns = ['Sleep_Quality', 'Headache_Frequency', 'Academic_Performance', 'Study_Load', 
                'Extracurricular_Activities', 'Stress_Level']

print(data.head())
print(data.shape)

# Define features and target variable
X = data.drop('Stress_Level', axis=1)  # All columns except the target
y = data['Stress_Level']  # Target variable

# Split into training and test data
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Data scaling
scaler = StandardScaler()
X_train = scaler.fit_transform(X_train)
X_test = scaler.transform(X_test)

# Convert target variable to categorical (if needed)
y_train = pd.get_dummies(y_train).values
y_test = pd.get_dummies(y_test).values

model = tf.keras.models.Sequential([
    tf.keras.layers.Dense(128, activation='relu', input_shape=(X_train.shape[1],)),
    tf.keras.layers.Dropout(0.2),  # Add dropout layer
    tf.keras.layers.Dense(64, activation='relu'),
    tf.keras.layers.Dropout(0.2),  # Add dropout layer
    tf.keras.layers.Dense(32, activation='relu'),
    tf.keras.layers.Dense(y_train.shape[1], activation='softmax')  # Use softmax for multi-class classification
])

# Compile the model
model.compile(optimizer=tf.keras.optimizers.Adam(learning_rate=0.001),  # Adjust learning rate
              loss='categorical_crossentropy', metrics=['accuracy'])

# Fit the model with early stopping to prevent overfitting
early_stopping = tf.keras.callbacks.EarlyStopping(monitor='val_loss', patience=5)

model.fit(X_train, y_train, epochs=50, batch_size=8, validation_split=0.2, callbacks=[early_stopping])  # More epochs

# Fit the model
model.fit(X_train, y_train, epochs=10, batch_size=8, validation_split=0.2)  # Adjust epochs and batch size as needed

# Make predictions
y_pred_prob = model.predict(X_test)
y_pred = np.argmax(y_pred_prob, axis=1)  # Get the class with the highest probability

# Convert y_test back to original labels if necessary
y_test_labels = np.argmax(y_test, axis=1)

# Evaluate the model
accuracy = accuracy_score(y_test_labels, y_pred)
confusion = confusion_matrix(y_test_labels, y_pred)

print("Accuracy:", accuracy)
print("Confusion Matrix:\n", confusion)

# Now you can save the Keras model and convert it to TensorFlow Lite
tf.keras.models.save_model(model, 'model.h5')
converter = tf.lite.TFLiteConverter.from_keras_model(model)
tflite_model = converter.convert()

with open('model.tflite', 'wb') as f:
    f.write(tflite_model)
