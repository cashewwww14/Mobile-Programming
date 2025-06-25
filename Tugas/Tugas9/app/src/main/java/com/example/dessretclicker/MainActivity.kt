package com.example.dessertclicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dessertclicker.ui.theme.DessertClickerTheme
import androidx.compose.ui.layout.ContentScale


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DessertClickerTheme {
                DessertClickerApp()
            }
        }
    }
}

data class Dessert(
    val imageRes: Int,
    val price: Int,
    val startProductionAmount: Int,
    val name: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DessertClickerApp() {
    // State variables
    var revenue by remember { mutableIntStateOf(0) }
    var dessertsSold by remember { mutableIntStateOf(0) }
    var currentDessertIndex by remember { mutableIntStateOf(0) }

    // Sample dessert data (using Android system drawables as placeholders)
    val desserts = listOf(
        Dessert(R.drawable.cupcake, 5, 0, "Cupcake"),
        Dessert(R.drawable.donut, 25, 5, "Donut"),
        Dessert(R.drawable.eclair, 100, 20, "Eclair"),
        Dessert(R.drawable.froyo, 500, 50, "Froyo"),
        Dessert(R.drawable.gingerbread, 2000, 100, "Gingerbread"),
        Dessert(R.drawable.honeycomb, 8000, 200, "Honeycomb"),
        Dessert(R.drawable.ice_cream, 16000, 500, "Ice Cream"),
        Dessert(R.drawable.jelly_bean, 36000, 1000, "Jelly Bean")
    )

    val currentDessert = desserts[currentDessertIndex]

    // Function to determine which dessert to show
    fun determineDessertToShow(desserts: List<Dessert>, dessertsSold: Int): Dessert {
        var dessertToShow = desserts.first()
        for (dessert in desserts) {
            if (dessertsSold >= dessert.startProductionAmount) {
                dessertToShow = dessert
            } else {
                break
            }
        }
        return dessertToShow
    }

    // Function to handle dessert click
    fun onDessertClicked() {
        revenue += currentDessert.price
        dessertsSold++

        val newDessert = determineDessertToShow(desserts, dessertsSold)
        currentDessertIndex = desserts.indexOf(newDessert)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Dessert Clicker",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "$revenue",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Dessert sold counter
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Desserts Sold",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = dessertsSold.toString(),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Current dessert display
            Card(
                modifier = Modifier.padding(bottom = 24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = currentDessert.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Clickable dessert image
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .clickable { onDessertClicked() },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = currentDessert.imageRes),
                            contentDescription = currentDessert.name,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Text(
                        text = "Click to sell for ${currentDessert.price}!",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 16.dp),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Progress indicator
            LinearProgressIndicator(
                progress = {
                    val nextDessertIndex = (currentDessertIndex + 1).coerceAtMost(desserts.size - 1)
                    if (nextDessertIndex < desserts.size && nextDessertIndex != currentDessertIndex) {
                        val nextDessert = desserts[nextDessertIndex]
                        val progress = (dessertsSold.toFloat() / nextDessert.startProductionAmount.toFloat()).coerceIn(0f, 1f)
                        progress
                    } else {
                        1f
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            )

            val nextDessertIndex = (currentDessertIndex + 1).coerceAtMost(desserts.size - 1)
            if (nextDessertIndex < desserts.size && nextDessertIndex != currentDessertIndex) {
                val nextDessert = desserts[nextDessertIndex]
                Text(
                    text = "Next: ${nextDessert.name} (${nextDessert.startProductionAmount} sold)",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                Text(
                    text = "You've unlocked all desserts! 🎉",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    revenue = 0
                    dessertsSold = 0
                    currentDessertIndex = 0
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Restart Game", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DessertClickerPreview() {
    DessertClickerTheme {
        DessertClickerApp()
    }
}