package com.example.booksport

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.example.booksport.ui.theme.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavHostController
import java.time.*
import java.time.format.DateTimeFormatter

@Composable
fun SelectTimeScreen(navController: NavHostController, sport: String, imageResId: Int, harga: Int) {
    var currentMonth by remember { mutableStateOf(YearMonth.of(2025, 5)) }
    val firstDayOfMonth = currentMonth.atDay(1)
    val daysInMonth = currentMonth.lengthOfMonth()
    val days = (1..daysInMonth).map { currentMonth.atDay(it) }

    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var showPopup by remember { mutableStateOf(false) }

    val selectedSessions = remember { mutableStateMapOf<LocalDate, MutableList<String>>() }
    val sessionPrice = harga

    // Fully booked weekdays (by day of week)
    val scheduleMap = mapOf(
        0 to listOf("07:00–10:00", "10:01–13:00"),
        1 to listOf("10:01–13:00", "13:01–16:00"),
        2 to listOf("13:01–16:00", "16:01–19:00"),
        3 to listOf("07:00–10:00"),
        4 to listOf("07:00–10:00", "10:01–13:00", "16:01–19:00"),
        5 to emptyList(), // fully booked
        6 to listOf("19:01–21:00")
    )

    val allTimes = listOf("07:00–10:00", "10:01–13:00", "13:01–16:00", "16:01–19:00", "19:01–21:00")
    val fullyBookedWeekdays = scheduleMap.filter { it.value.isEmpty() }.keys
    val randomFullyBookedDates = setOf(5, 12, 18, 25)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .padding(16.dp)
    ) {
        // IMAGE
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // TITLE
        Text(
            text = "Lapangan $sport",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Jeans
        )

        Spacer(modifier = Modifier.height(16.dp))

        // MONTH NAVIGATION
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = {
                currentMonth = currentMonth.minusMonths(1)
                selectedDate = null
            }) {
                Text("←", fontSize = 20.sp, color = Jeans)
            }

            Text(
                text = currentMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Jeans
            )

            TextButton(onClick = {
                currentMonth = currentMonth.plusMonths(1)
                selectedDate = null
            }) {
                Text("→", fontSize = 20.sp, color = Jeans)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // DAY HEADERS
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach { day ->
                Text(
                    text = day,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Jeans,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }

        // CALENDAR GRID
        val startDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7
        val totalCells = days.size + startDayOfWeek
        val rowCount = (totalCells + 6) / 7

        repeat(rowCount) { rowIndex ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(7) { colIndex ->
                    val cellIndex = rowIndex * 7 + colIndex
                    if (cellIndex >= startDayOfWeek && cellIndex - startDayOfWeek < days.size) {
                        val date = days[cellIndex - startDayOfWeek]
                        val isSelected = selectedDate == date
                        val isFullyBooked =
                            fullyBookedWeekdays.contains(date.dayOfWeek.value % 7) ||
                                    randomFullyBookedDates.contains(date.dayOfMonth)

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Transparent)
                                .clickable(enabled = !isFullyBooked) {
                                    selectedDate = date
                                    showPopup = true
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        when {
                                            isSelected -> Jeans
                                            isFullyBooked -> Color.LightGray
                                            else -> Color.Transparent
                                        }
                                    )
                                    .padding(vertical = 12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = date.dayOfMonth.toString(),
                                    color = when {
                                        isSelected -> Color.White
                                        isFullyBooked -> Color.Gray
                                        else -> Jeans
                                    },
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    } else {
                        Box(modifier = Modifier.weight(1f)) {}
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // BOTTOM BUTTONS
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .padding(end = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BabyJeans)
            ) {
                Text("Back", color = Jeans, fontWeight = FontWeight.Bold)
            }

            val totalSessions = selectedSessions.values.sumOf { it.size }
            val totalPrice = totalSessions * sessionPrice

            Button(
                onClick = {
                    val details = selectedSessions.entries.joinToString("; ") {
                        val dateStr = it.key.format(DateTimeFormatter.ofPattern("d MMM"))
                        "$dateStr: ${it.value.joinToString(", ")}"
                    }
                    navController.navigate("booking_confirm/$sport/$details/$totalPrice")
                },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .padding(start = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Jeans),
                enabled = totalSessions > 0
            ) {
                Text("Checkout ($totalPrice)", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }

        // POPUP TIME SELECTION
        if (showPopup && selectedDate != null) {
            val selectedDayOfWeek = selectedDate!!.dayOfWeek.value % 7
            val availableTimes = scheduleMap[selectedDayOfWeek] ?: emptyList()
            val unavailableTimes = allTimes - availableTimes

            AlertDialog(
                onDismissRequest = { showPopup = false },
                title = { Text("Select Sessions for ${selectedDate!!.format(DateTimeFormatter.ofPattern("d MMM"))}") },
                text = {
                    Column {
                        availableTimes.forEach { time ->
                            val isSelected = selectedSessions[selectedDate]?.contains(time) == true
                            OutlinedButton(
                                onClick = {
                                    val sessions = selectedSessions.getOrPut(selectedDate!!) { mutableListOf() }
                                    if (isSelected) {
                                        sessions.remove(time)
                                        if (sessions.isEmpty()) selectedSessions.remove(selectedDate!!)
                                    } else {
                                        sessions.add(time)
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, if (isSelected) Jeans else Color.LightGray),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = if (isSelected) Jeans.copy(alpha = 0.2f) else Color.Transparent,
                                    contentColor = if (isSelected) Jeans else Color.Gray
                                )
                            ) {
                                Text(text = time, fontWeight = FontWeight.Bold)
                            }
                        }

                        if (unavailableTimes.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Unavailable times", color = Color.Gray, fontSize = 14.sp)
                            unavailableTimes.forEach {
                                OutlinedButton(
                                    onClick = {},
                                    enabled = false,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    border = BorderStroke(1.dp, Color.LightGray),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        containerColor = Color.LightGray,
                                        contentColor = Color.Gray
                                    )
                                ) {
                                    Text(it)
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showPopup = false }) {
                        Text("Done", color = Jeans)
                    }
                }
            )
        }
    }
}
