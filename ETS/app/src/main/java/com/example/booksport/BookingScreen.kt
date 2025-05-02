package com.example.booksport

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.booksport.ui.theme.*

data class FieldDetail(
    val description: String,
    val reviewerName: String,
    val reviewText: String,
    val rating: String,
    val price: String,
    val avatarRes: Int
)

val fieldDetails = mapOf(
    "Badminton" to FieldDetail(
        "Lapangan Badminton is an indoor court located in a strategic city center area and meets international standards for games.",
        "Paulina", "Clean and well-organized venue", "4.9", "Rp65,000", R.drawable.lapanganbadminton
    ),
    "Futsal" to FieldDetail(
        "Lapangan Futsal features artificial turf and enclosed walls, perfect for competitive or casual play.",
        "Andi", "Great turf quality and nice atmosphere", "5.0", "Rp50,000", R.drawable.lapanganfutsal
    ),
    "Basket" to FieldDetail(
        "Lapangan Basket is a standard-sized court with great lighting and surface quality for serious players.",
        "Rina", "Nice floor, perfect for 3v3 or full court", "4.8", "Rp55,000", R.drawable.lapanganbasket
    ),
    "Tenis" to FieldDetail(
        "Lapangan Tenis has a clean hardcourt surface, surrounded by greenery, ideal for day or night matches.",
        "Dewi", "Calm environment, good surface", "4.7", "Rp70,000", R.drawable.lapangantenis
    ),
    "Kolam Renang" to FieldDetail(
        "Kolam Renang is a semi-olympic pool with clear water, suitable for swimming, training, or family fun.",
        "Budi", "Clean pool and locker area", "4.9", "Rp45,000", R.drawable.kolamrenang
    )
)

@Composable
fun BookingScreen(navController: NavHostController, sport: String, imageResId: Int) {
    var name by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    val detail = fieldDetails[sport] ?: FieldDetail(
        "Fasilitas olahraga yang lengkap dan nyaman.",
        "Anonim", "Tempat bagus", "4.5", "Rp50,000", R.drawable.kolamrenang
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = "Lapangan $sport",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Poppins,
                color = DarkGreen
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "⭐ ${detail.rating}/5   Kota Jakarta Pusat",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = detail.description,
                fontSize = 15.sp,
                color = Color.DarkGray,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "From our customer",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = DarkGreen
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = detail.avatarRes),
                    contentDescription = "Customer",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(detail.reviewerName, fontWeight = FontWeight.Bold)
                    Text(detail.reviewText, fontSize = 13.sp, color = TextGray)
                }
                Text(detail.rating, fontWeight = FontWeight.Bold, color = DarkGreen)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    navController.navigate("select_time/${sport}/${imageResId}")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = DarkGreen)
            ) {
                Text(
                    text = "Book",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = detail.price,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = DarkGreen
            )
            Text("Per Sesi", fontSize = 14.sp, color = TextGray)
        }
    }
}
