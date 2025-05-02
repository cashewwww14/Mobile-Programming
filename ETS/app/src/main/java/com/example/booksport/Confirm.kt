package com.example.booksport

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import com.example.booksport.ui.theme.Jeans
import com.example.booksport.ui.theme.BabyJeans
import com.example.booksport.ui.theme.Typography
import androidx.navigation.NavHostController

@Composable
fun ConfirmScreen(navController: NavHostController, name: String, sport: String, date: String, time: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Success",
            tint = Jeans,
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Booking Berhasil!",
            style = Typography.headlineLarge.copy(fontSize = 28.sp),
            color = Jeans,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF6F6F6)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(text = "Nama: $name", style = Typography.bodyLarge)
                Text(text = "Lapangan: $sport", style = Typography.bodyLarge)
                Text(text = "Tanggal: $date", style = Typography.bodyLarge)
                Text(text = "Waktu: $time", style = Typography.bodyLarge)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Home Button
        Button(
            onClick = {
                // Navigate back to the home screen
                navController.navigate("home") {
                    popUpTo("home") { inclusive = true } // Clear the back stack
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BabyJeans)
        ) {
            Text("Home", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Jeans)
        }
    }
}
