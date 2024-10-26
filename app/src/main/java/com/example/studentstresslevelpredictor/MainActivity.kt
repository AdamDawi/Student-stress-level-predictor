package com.example.studentstresslevelpredictor

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studentstresslevelpredictor.ui.theme.StudentStressLevelPredictorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudentStressLevelPredictorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    StudentDataForm(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun StudentDataForm(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val predictionModel = remember { PredictionModel(context) }
    val prediction = remember { mutableStateOf<FloatArray?>(null) }
    var sleepQuality by remember { mutableStateOf("") }
    var headacheFrequency by remember { mutableStateOf("") }
    var academicPerformance by remember { mutableStateOf("") }
    var studyLoad by remember { mutableStateOf("") }
    var extracurricularActivities by remember { mutableStateOf("") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null // Remove ripple effect
            ) {
                focusManager.clearFocus()
            }
            .background(Color.Black)
            .padding(horizontal = 16.dp)
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Stress Level Predictor",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Give answers in a scale of 1 to 5",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                InputTextField(
                    label = "Rate your Sleep Quality \uD83D\uDE34",
                    value = sleepQuality,
                    onValueChange = { sleepQuality = it },
                    onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Down) }
                )
                InputTextField(
                    label = "How many times a week do you suffer headaches \uD83E\uDD15?",
                    value = headacheFrequency,
                    onValueChange = { headacheFrequency = it },
                    onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Down) }
                )
                InputTextField(
                    label = "How would you rate your academic performance \uD83D\uDC69\u200D\uD83C\uDF93?",
                    value = academicPerformance,
                    onValueChange = { academicPerformance = it },
                    onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Down) }
                )
                InputTextField(
                    label = "How would you rate your study load?",
                    value = studyLoad,
                    onValueChange = { studyLoad = it },
                    onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Down) }
                )
                InputTextField(
                    label = "How many times a week do you practice extracurricular activities \uD83C\uDFBE?",
                    value = extracurricularActivities,
                    onValueChange = { extracurricularActivities = it },
                    imeAction = ImeAction.Done,
                    onDone = {focusManager.clearFocus()}
                )
            }
        }
        item {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(48.dp))
                Text(
                    text = "Your stress level: ${
                        (prediction.value?.indices?.maxByOrNull { prediction.value!![it] }
                            ?.plus(1)) ?: "N/A"
                    }",
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    val studentData = arrayOf(
                        sleepQuality,
                        headacheFrequency,
                        academicPerformance,
                        studyLoad,
                        extracurricularActivities
                    )
                    prediction.value = predictionModel.predict(studentData)
                    Log.e("Prediction", prediction.value.contentToString())
                }) {
                    Text("Send data to predict")
                }
            }
        }
    }
}

@Composable
fun InputTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Next,
    onNext: () -> Unit = {},
    onDone: () -> Unit = {},
) {
    OutlinedTextField(
        value = value,
        onValueChange = { text ->
            onValueChange(text)
        },
        label = {
            Text(
                label,
                color = Color.White.copy(alpha = 0.6f)
            ) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction // Set the action to Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                onNext() // Trigger the next action
            },
            onDone = {
                onDone() // Clear focus when done
            }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = OutlinedTextFieldDefaults.colors().copy(
            errorTextColor = Color.White,
            unfocusedTextColor = Color.White,
            disabledTextColor = Color.White,
            focusedTextColor = Color.White,
            errorSupportingTextColor = Color.White,
            focusedSupportingTextColor = Color.White,
            disabledSupportingTextColor = Color.White,
            unfocusedSupportingTextColor = Color.White
        )
    )
}

@Preview
@Composable
private fun StudentDataFormPreview() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        StudentDataForm(modifier = Modifier.padding(innerPadding))
    }
}
