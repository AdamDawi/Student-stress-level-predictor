package com.example.studentstresslevelpredictor

import android.content.Context
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil

class PredictionModel(
    private val context: Context
) {
    private val interpreter: Interpreter

    init {
        interpreter = createInterpreter()
    }

    fun predict(inputData: Array<String>): FloatArray? {
        for(input in inputData){
            if(input !in listOf("1","2","3","4","5")){
                return null
            }
        }
        val reshapedInputData = Array(1) { inputData.map { it.toFloat() }.toFloatArray() }
        val outputData = Array(1) { FloatArray(5) }
        interpreter.run(reshapedInputData, outputData)

        return outputData[0] //only first row of output
    }

    private fun createInterpreter(): Interpreter {
        val tfLiteOptions = Interpreter.Options() //can be configure to use GPUDelegate
        return Interpreter(FileUtil.loadMappedFile(context, MODEL_FILENAME), tfLiteOptions)
    }

    companion object{
        const val MODEL_FILENAME = "model.tflite"
    }
}