package com.example.booksport

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.booksport.ui.theme.*

val SoftBackground = Color(0xFFF6F6F6)

data class Lapangan(
    val nama: String,
    val kategori: String,
    val rating: Double,
    val lokasi: String,
    val harga: Int,
    val gambar: Int
)

val daftarLapangan = listOf(
    Lapangan("Lapangan Tenis", "Tenis", 5.0, "Sukolilo", 100000, R.drawable.ltenis),
    Lapangan("Jogging Track", "Jogging", 4.4, "Lapangan Jatim Seger", 250000, R.drawable.llari),
    Lapangan("Lapangan Futsal", "Futsal", 4.8, "Fiva Futsal", 75000, R.drawable.lfutsal),
    Lapangan("Lapangan Basket", "Basket", 4.7, "Mulyosari", 120000, R.drawable.lbasket),
    Lapangan("Lapangan Panahan", "Panahan", 4.85, "Hutan ITS", 25000, R.drawable.lpanahan)
)

val fieldDetails = mapOf(
    "Tenis" to "Lapangan tenis outdoor profesional dengan permukaan terawat, garis standar internasional, tersedia bola tenis berkualitas, pencahayaan memadai, dan area duduk, ideal untuk latihan maupun pertandingan.",
    "Jogging" to "Lintasan jogging outdoor profesional dengan permukaan sintetis terawat, jalur jelas bertanda, tersedia bola latihan, pencahayaan memadai, dan area istirahat, ideal untuk olahraga santai maupun latihan serius.",
    "Futsal" to "Lapangan futsal indoor berkualitas dengan rumput sintetis terawat, garis lapangan standar, bola futsal tersedia, pencahayaan terang, serta area duduk penonton, ideal untuk latihan maupun pertandingan.",
    "Basket" to "Lapangan basket indoor profesional dengan lantai kayu terawat, garis lapangan standar, bola basket tersedia, ring berkualitas, pencahayaan optimal, dan area duduk penonton, ideal untuk latihan maupun kompetisi.",
    "Panahan" to "Lapangan panahan outdoor profesional dengan target standar, permukaan rumput terawat, anak panah dan busur tersedia, area aman berpagar, serta pencahayaan memadai, ideal untuk latihan maupun turnamen."
)
@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .padding(16.dp)
    ) {
        // Header - Logo dan Nama Aplikasi di Tengah Atas
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(45.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "FieldSpot",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                color = BlueJeans
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Solusi cepat booking lapangan!",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = BlueJeans
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Temukan dan pilih lapangan yang tersedia.",
            fontSize = 16.sp,
            color = TextGray
        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(daftarLapangan) { lapangan ->
                Card(
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(12.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamBackground),
                    modifier = Modifier
                        .width(300.dp)
                        .height(550.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Image(
                            painter = painterResource(id = lapangan.gambar),
                            contentDescription = lapangan.nama,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = lapangan.nama,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "⭐ ${lapangan.rating} • ${lapangan.lokasi}",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Rp${"%,d".format(lapangan.harga)}/ sesi",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Jeans
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = fieldDetails[lapangan.kategori] ?: "Deskripsi tidak tersedia",
                            fontSize = 14.sp,
                            color = Color.DarkGray,
                            lineHeight = 20.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Tombol Book Now di Paling Bawah
                        Spacer(modifier = Modifier.weight(1f)) // Push tombol ke bawah
                        Button(
                            onClick = {
                                navController.navigate("select_time/${lapangan.kategori}/${lapangan.gambar}/${lapangan.harga}")
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = BlueJeans),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(text = "Book Now!", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}
