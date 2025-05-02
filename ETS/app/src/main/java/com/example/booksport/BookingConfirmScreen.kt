package com.example.booksport

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.booksport.ui.theme.*

@Composable
fun BookingConfirmScreen(
    navController: NavHostController,
    sport: String,
    details: String, // Added this to receive the selected details from SelectTimeScreen
    totalPrice: Int
) {
    val selectedSessions = details.split("; ").map {
        val parts = it.split(": ")
        parts[0] to parts[1].split(", ")
    }.toMap()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .padding(24.dp)
    ) {
        // Title
        Text(
            text = "Lapangan $sport",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = DarkGreen
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Jadwal Terpilih
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CreamBackground),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Lapangan $sport", fontWeight = FontWeight.Bold, color = DarkGreen)
                selectedSessions.forEach { (date, times) ->
                    Text("$date • ${times.joinToString(", ")}", color = Color.Gray)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Add More Sessions Button
        Text(
            text = "+ Tambah Jadwal",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = DarkGreen
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Biaya Section
        Text("Rincian Biaya", fontWeight = FontWeight.Bold, color = DarkGreen)
        Spacer(modifier = Modifier.height(12.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Biaya Sewa")
                Text("Rp${totalPrice}")
            }
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Total Bayar", fontWeight = FontWeight.Bold)
                Text("Rp${totalPrice}", fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Pembayaran Section
        Text("Atur Pembayaran", fontWeight = FontWeight.Bold, color = DarkGreen)
        Text("Kebijakan Reschedule & Pembatalan", fontSize = 14.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(24.dp))

        // Booking Button
        Button(
            onClick = {
                // Arahkan ke halaman konfirmasi/selesai dengan data yang sesuai
                navController.navigate("confirm/Anda/${sport}/${selectedSessions.keys.first()}/${selectedSessions.values.first().joinToString(", ")}")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Jeans) // Adjust color here
        ) {
            Text("Booking", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
        }


        Spacer(modifier = Modifier.height(16.dp)) // Add space between buttons

        // Back Button
        Button(
            onClick = { navController.popBackStack() }, // Navigates back to the previous screen
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BabyJeans) // Adjust color here
        ) {
            Text("Kembali", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Jeans)
        }
    }
}
