package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var resultText: TextView
    private var currentNumber = ""
    private var previousNumber = ""
    private var operation = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultText = findViewById(R.id.resultText)

        // Initialize number buttons
        val buttons = listOf(
            findViewById<Button>(R.id.btnZero),
            findViewById<Button>(R.id.btnOne),
            findViewById<Button>(R.id.btnTwo),
            findViewById<Button>(R.id.btnThree),
            findViewById<Button>(R.id.btnFour),
            findViewById<Button>(R.id.btnFive),
            findViewById<Button>(R.id.btnSix),
            findViewById<Button>(R.id.btnSeven),
            findViewById<Button>(R.id.btnEight),
            findViewById<Button>(R.id.btnNine),
            findViewById<Button>(R.id.btnDecimal)
        )

        // Set click listeners for number buttons
        buttons.forEach { button ->
            button.setOnClickListener {
                appendNumber(button.text.toString())
            }
        }

        // Set click listeners for operation buttons
        findViewById<Button>(R.id.btnAdd).setOnClickListener { selectOperation("+") }
        findViewById<Button>(R.id.btnSubtract).setOnClickListener { selectOperation("-") }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { selectOperation("*") }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { selectOperation("/") }

        // Set click listener for equals button
        findViewById<Button>(R.id.btnEquals).setOnClickListener {
            if (operation.isNotEmpty() && currentNumber.isNotEmpty()) {
                val result = calculate(previousNumber, currentNumber, operation)
                updateResultText(result)
                previousNumber = result.toString()
                currentNumber = ""
                operation = ""
            }
        }

        // Set click listener for clear button
        findViewById<Button>(R.id.btnClear).setOnClickListener {
            clear()
        }
    }

    private fun appendNumber(number: String) {
        if (number == "." && currentNumber.contains(".")) return
        currentNumber += number
        resultText.text = currentNumber
    }

    private fun selectOperation(op: String) {
        if (currentNumber.isNotEmpty()) {
            if (previousNumber.isNotEmpty()) {
                val result = calculate(previousNumber, currentNumber, operation)
                previousNumber = result.toString()
                updateResultText(result)
            } else {
                previousNumber = currentNumber
            }
            currentNumber = ""
            operation = op
            resultText.text = "$previousNumber $operation"
        }
    }

    private fun calculate(num1: String, num2: String, operation: String): Double {
        val firstNum = num1.toDoubleOrNull()
        val secondNum = num2.toDoubleOrNull()

        return when {
            firstNum != null && secondNum != null -> when (operation) {
                "+" -> firstNum + secondNum
                "-" -> firstNum - secondNum
                "*" -> firstNum * secondNum
                "/" -> if (secondNum != 0.0) firstNum / secondNum else Double.NaN
                else -> Double.NaN
            }
            else -> Double.NaN
        }
    }

    private fun updateResultText(result: Double) {
        resultText.text = result.toString()
    }

    private fun clear() {
        currentNumber = ""
        previousNumber = ""
        operation = ""
        resultText.text = "0"
    }
}
