package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalculatorApp()
                }
            }
        }
    }
}

@Composable
fun CalculatorApp() {
    var display by remember { mutableStateOf("0") }
    var previousNumber by remember { mutableStateOf(0.0) }
    var operation by remember { mutableStateOf("") }
    var waitingForNumber by remember { mutableStateOf(false) }

    fun onNumberClick(number: String) {
        if (waitingForNumber) {
            display = number
            waitingForNumber = false
        } else {
            display = if (display == "0") number else display + number
        }
    }

    fun onOperationClick(op: String) {
        if (operation.isNotEmpty() && !waitingForNumber) {
            val currentNumber = display.toDoubleOrNull() ?: 0.0
            val result = when (operation) {
                "+" -> previousNumber + currentNumber
                "-" -> previousNumber - currentNumber
                "×" -> previousNumber * currentNumber
                "÷" -> if (currentNumber != 0.0) previousNumber / currentNumber else 0.0
                else -> currentNumber
            }
            display = if (result == result.toInt().toDouble()) {
                result.toInt().toString()
            } else {
                String.format("%.8f", result).trimEnd('0').trimEnd('.')
            }
            previousNumber = result
        } else {
            previousNumber = display.toDoubleOrNull() ?: 0.0
        }
        operation = op
        waitingForNumber = true
    }

    fun onEqualsClick() {
        if (operation.isNotEmpty()) {
            onOperationClick("")
            operation = ""
        }
    }

    fun onClearClick() {
        display = "0"
        previousNumber = 0.0
        operation = ""
        waitingForNumber = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = display,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.End,
                    modifier = Modifier.padding(horizontal = 24.dp),
                    maxLines = 1
                )
            }
        }

        // Button grid
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // Row 1: C and operations
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                CalculatorButton(
                    text = "C",
                    onClick = { onClearClick() },
                    modifier = Modifier.weight(2f),
                    backgroundColor = MaterialTheme.colorScheme.error,
                    textColor = MaterialTheme.colorScheme.onError
                )
                CalculatorButton(
                    text = "÷",
                    onClick = { onOperationClick("÷") },
                    modifier = Modifier.weight(1f),
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    textColor = MaterialTheme.colorScheme.onPrimary
                )
                CalculatorButton(
                    text = "×",
                    onClick = { onOperationClick("×") },
                    modifier = Modifier.weight(1f),
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    textColor = MaterialTheme.colorScheme.onPrimary
                )
            }

            // Row 2: 7, 8, 9, -
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                CalculatorButton("7", { onNumberClick("7") }, Modifier.weight(1f))
                CalculatorButton("8", { onNumberClick("8") }, Modifier.weight(1f))
                CalculatorButton("9", { onNumberClick("9") }, Modifier.weight(1f))
                CalculatorButton(
                    text = "-",
                    onClick = { onOperationClick("-") },
                    modifier = Modifier.weight(1f),
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    textColor = MaterialTheme.colorScheme.onPrimary
                )
            }

            // Row 3: 4, 5, 6, +
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                CalculatorButton("4", { onNumberClick("4") }, Modifier.weight(1f))
                CalculatorButton("5", { onNumberClick("5") }, Modifier.weight(1f))
                CalculatorButton("6", { onNumberClick("6") }, Modifier.weight(1f))
                CalculatorButton(
                    text = "+",
                    onClick = { onOperationClick("+") },
                    modifier = Modifier.weight(1f),
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    textColor = MaterialTheme.colorScheme.onPrimary
                )
            }

            // Row 4: 1, 2, 3, =
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                CalculatorButton("1", { onNumberClick("1") }, Modifier.weight(1f))
                CalculatorButton("2", { onNumberClick("2") }, Modifier.weight(1f))
                CalculatorButton("3", { onNumberClick("3") }, Modifier.weight(1f))
                CalculatorButton(
                    text = "=",
                    onClick = { onEqualsClick() },
                    modifier = Modifier.weight(1f),
                    backgroundColor = MaterialTheme.colorScheme.secondary,
                    textColor = MaterialTheme.colorScheme.onSecondary
                )
            }

            // Row 5: 0
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                CalculatorButton(
                    text = "0",
                    onClick = { onNumberClick("0") },
                    modifier = Modifier.weight(4f)
                )
            }
        }
    }
}

@Composable
fun CalculatorButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(70.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
