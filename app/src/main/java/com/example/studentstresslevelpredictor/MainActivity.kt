package com.example.studentstresslevelpredictor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.studentstresslevelpredictor.student_form_screen.StudentFormScreen
import com.example.studentstresslevelpredictor.ui.theme.StudentStressLevelPredictorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudentStressLevelPredictorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    StudentFormScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}