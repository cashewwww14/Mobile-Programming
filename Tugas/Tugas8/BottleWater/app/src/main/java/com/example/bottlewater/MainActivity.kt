package com.example.bottlewater

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.bottlewater.ui.theme.BottleWaterTheme
import kotlin.math.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BottleWaterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFE8F4FD)
                ) {
                    WaterBottleApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaterBottleApp() {
    var currentWaterLevel by remember { mutableIntStateOf(0) }
    var selectedAmount by remember { mutableStateOf("200ml") }
    var expanded by remember { mutableStateOf(false) }
    var showCongrats by remember { mutableStateOf(false) }
    
    val maxCapacity = 2000
    val drinkAmounts = listOf("50ml", "100ml", "200ml", "250ml", "300ml", "500ml")
    
    // Smooth water level animation
    val animatedWaterLevel by animateIntAsState(
        targetValue = currentWaterLevel,
        animationSpec = tween(
            durationMillis = 1200,
            easing = FastOutSlowInEasing
        ),
        label = "water_level"
    )
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE8F4FD),
                        Color(0xFFD1E9F6)
                    )
                )
            )
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        
        // Clean Title
        Text(
            text = "💧 Water Tracker",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1565C0),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        
        Text(
            text = "Stay hydrated, stay healthy!",
            fontSize = 16.sp,
            color = Color(0xFF666666),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        
        // Progress Card
        SmoothProgressCard(
            currentLevel = currentWaterLevel,
            maxCapacity = maxCapacity
        )
        
        Spacer(modifier = Modifier.height(30.dp))
        
        // Smooth Water Bottle - Perfect Cropping
        Box(
            modifier = Modifier.size(width = 200.dp, height = 380.dp),
            contentAlignment = Alignment.Center
        ) {
            SmoothWaterBottle(
                waterLevel = animatedWaterLevel,
                maxCapacity = maxCapacity
            )
        }
        
        Spacer(modifier = Modifier.height(30.dp))
        
        // Clean Controls
        SmoothControlsCard(
            selectedAmount = selectedAmount,
            drinkAmounts = drinkAmounts,
            expanded = expanded,
            onExpandedChange = { expanded = it },
            onAmountSelect = { selectedAmount = it; expanded = false },
            onDrink = {
                val amount = selectedAmount.replace("ml", "").toInt()
                if (currentWaterLevel + amount <= maxCapacity) {
                    currentWaterLevel += amount
                    if (currentWaterLevel >= maxCapacity) {
                        showCongrats = true
                    }
                } else {
                    val remaining = maxCapacity - currentWaterLevel
                    if (remaining > 0) {
                        currentWaterLevel = maxCapacity
                        showCongrats = true
                    }
                }
            },
            onEmpty = { currentWaterLevel = 0 },
            canDrink = currentWaterLevel < maxCapacity,
            canEmpty = currentWaterLevel > 0
        )
    }
    
    // Simple Congratulations Dialog
    if (showCongrats) {
        SmoothCongratsDialog(
            onDismiss = { showCongrats = false }
        )
    }
}

@Composable
fun SmoothProgressCard(
    currentLevel: Int,
    maxCapacity: Int
) {
    val percentage = (currentLevel.toFloat() / maxCapacity * 100).toInt()
    
    val progressAnimation by animateFloatAsState(
        targetValue = currentLevel.toFloat() / maxCapacity,
        animationSpec = tween(
            durationMillis = 1200,
            easing = FastOutSlowInEasing
        ),
        label = "progress"
    )
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.95f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${currentLevel}ml / ${maxCapacity}ml",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1565C0)
            )
            
            Text(
                text = "$percentage% Complete",
                fontSize = 16.sp,
                color = Color(0xFF666666),
                modifier = Modifier.padding(vertical = 8.dp)
            )
            
            // Smooth Progress Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color(0xFFE0E0E0))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(progressAnimation)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF4FC3F7),
                                    Color(0xFF29B6F6),
                                    Color(0xFF0288D1)
                                )
                            )
                        )
                        .clip(RoundedCornerShape(5.dp))
                )
            }
        }
    }
}

@Composable
fun SmoothWaterBottle(
    waterLevel: Int,
    maxCapacity: Int
) {
    // Gentle wave animation
    val infiniteTransition = rememberInfiniteTransition(label = "water_animation")
    
    val waveOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 6.28f, // 2 * PI as Float
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave"
    )
    
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        val bottleWidth = size.width * 0.75f
        val bottleHeight = size.height * 0.85f
        val bottleX = (size.width - bottleWidth) / 2
        val bottleY = size.height * 0.08f
        
        // Calculate water dimensions - PERFECT CROPPING
        val waterLevel01 = (waterLevel.toFloat() / maxCapacity).coerceIn(0f, 1f)
        val usableBottleHeight = bottleHeight - 30.dp.toPx() // Minus cap and bottom margin
        val waterHeight = waterLevel01 * usableBottleHeight
        val waterY = bottleY + bottleHeight - waterHeight - 15.dp.toPx()
        val waterWidth = bottleWidth - 12.dp.toPx() // Perfect fit inside bottle
        val waterX = bottleX + 6.dp.toPx()
        
        // Draw water first (behind bottle)
        if (waterLevel > 0) {
            drawSmoothWater(
                x = waterX,
                y = waterY,
                width = waterWidth,
                height = waterHeight,
                waterLevel = waterLevel01,
                waveOffset = waveOffset
            )
        }
        
        // Draw bottle outline (over water for perfect cropping)
        drawBottleOutline(
            x = bottleX,
            y = bottleY,
            width = bottleWidth,
            height = bottleHeight
        )
        
        // Draw bottle cap
        drawBottleCap(
            x = bottleX + bottleWidth * 0.3f,
            y = bottleY - 8.dp.toPx(),
            width = bottleWidth * 0.4f,
            height = 20.dp.toPx()
        )
        
        // Draw volume markers
        drawVolumeMarkers(
            bottleX = bottleX,
            bottleY = bottleY,
            bottleHeight = usableBottleHeight
        )
    }
}

private fun DrawScope.drawSmoothWater(
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    waterLevel: Float,
    waveOffset: Float
) {
    // Smooth water colors based on level
    val (topColor, bottomColor) = when {
        waterLevel < 0.3f -> Color(0xFF81D4FA) to Color(0xFF29B6F6)
        waterLevel < 0.6f -> Color(0xFF29B6F6) to Color(0xFF0288D1)
        waterLevel < 0.9f -> Color(0xFF0288D1) to Color(0xFF0277BD)
        else -> Color(0xFF0277BD) to Color(0xFF01579B)
    }
    
    // Create smooth water path with gentle waves
    val waterPath = Path()
    
    // Start from bottom left
    waterPath.moveTo(x, y + height)
    
    // Left edge
    waterPath.lineTo(x, y + 12.dp.toPx())
    
    // Top edge with gentle waves
    val waveHeight = 4.dp.toPx() // Subtle waves
    val steps = 40
    
    for (i in 0..steps) {
        val xPos = x + (i.toFloat() / steps) * width
        val wave = sin(waveOffset + (i.toFloat() / steps) * 9.42f) * waveHeight // 3 * PI as Float
        val yPos = y + wave
        waterPath.lineTo(xPos, yPos)
    }
    
    // Right edge
    waterPath.lineTo(x + width, y + 12.dp.toPx())
    waterPath.lineTo(x + width, y + height)
    
    // Bottom edge with rounded corners
    waterPath.close()
    
    // Draw water with smooth gradient
    drawPath(
        path = waterPath,
        brush = Brush.verticalGradient(
            colors = listOf(
                topColor.copy(alpha = 0.9f),
                bottomColor.copy(alpha = 0.95f)
            ),
            startY = y,
            endY = y + height
        )
    )
    
    // Add subtle surface highlight
    val highlightY = y + sin(waveOffset) * 2.dp.toPx()
    drawLine(
        color = Color.White.copy(alpha = 0.3f),
        start = Offset(x + 20.dp.toPx(), highlightY),
        end = Offset(x + width - 20.dp.toPx(), highlightY),
        strokeWidth = 1.5.dp.toPx()
    )
}

private fun DrawScope.drawBottleOutline(
    x: Float,
    y: Float,
    width: Float,
    height: Float
) {
    // Bottle outline with perfect rounded corners
    drawRoundRect(
        color = Color(0xFF1565C0),
        topLeft = Offset(x, y),
        size = Size(width, height),
        cornerRadius = CornerRadius(20.dp.toPx()),
        style = Stroke(width = 4.dp.toPx())
    )
    
    // Inner highlight for 3D effect
    drawRoundRect(
        color = Color.White.copy(alpha = 0.3f),
        topLeft = Offset(x + 3.dp.toPx(), y + 3.dp.toPx()),
        size = Size(width - 6.dp.toPx(), height - 6.dp.toPx()),
        cornerRadius = CornerRadius(18.dp.toPx()),
        style = Stroke(width = 1.dp.toPx())
    )
}

private fun DrawScope.drawBottleCap(
    x: Float,
    y: Float,
    width: Float,
    height: Float
) {
    // Cap with gradient
    drawRoundRect(
        brush = Brush.verticalGradient(
            colors = listOf(
                Color(0xFF2196F3),
                Color(0xFF1565C0)
            )
        ),
        topLeft = Offset(x, y),
        size = Size(width, height),
        cornerRadius = CornerRadius(10.dp.toPx())
    )
    
    // Cap highlight
    drawRoundRect(
        color = Color.White.copy(alpha = 0.4f),
        topLeft = Offset(x + 2.dp.toPx(), y + 2.dp.toPx()),
        size = Size(width - 4.dp.toPx(), height * 0.4f),
        cornerRadius = CornerRadius(8.dp.toPx())
    )
}

private fun DrawScope.drawVolumeMarkers(
    bottleX: Float,
    bottleY: Float,
    bottleHeight: Float
) {
    val markers = listOf("2L", "1.5L", "1L", "0.5L", "0L")
    
    markers.forEachIndexed { index, _ ->
        val markerY = bottleY + (index.toFloat() / (markers.size - 1)) * bottleHeight
        
        // Marker line
        drawLine(
            color = Color(0xFF1976D2).copy(alpha = 0.7f),
            start = Offset(bottleX - 12.dp.toPx(), markerY),
            end = Offset(bottleX - 4.dp.toPx(), markerY),
            strokeWidth = 2.dp.toPx()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmoothControlsCard(
    selectedAmount: String,
    drinkAmounts: List<String>,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onAmountSelect: (String) -> Unit,
    onDrink: () -> Unit,
    onEmpty: () -> Unit,
    canDrink: Boolean,
    canEmpty: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.95f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Amount Selection Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Jumlah:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF1565C0)
                )
                
                // Clean Dropdown
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = onExpandedChange,
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = selectedAmount,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                modifier = Modifier.rotate(if (expanded) 180f else 0f),
                                tint = Color(0xFF1565C0)
                            )
                        },
                        modifier = Modifier.menuAnchor(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF2196F3),
                            unfocusedBorderColor = Color(0xFF2196F3),
                            focusedTextColor = Color(0xFF1565C0),
                            unfocusedTextColor = Color(0xFF1565C0)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                    
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { onExpandedChange(false) }
                    ) {
                        drinkAmounts.forEach { amount ->
                            DropdownMenuItem(
                                text = { 
                                    Text(
                                        amount,
                                        color = Color(0xFF1565C0),
                                        fontWeight = FontWeight.Medium
                                    ) 
                                },
                                onClick = { onAmountSelect(amount) }
                            )
                        }
                    }
                }
                
                // Clean Drink Button
                Button(
                    onClick = onDrink,
                    enabled = canDrink,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (canDrink) Color(0xFF2196F3) else Color(0xFFBDBDBD),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(20.dp),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = if (canDrink) 4.dp else 1.dp
                    )
                ) {
                    Text(
                        text = "💧 Drink",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Clean Empty Button
            Button(
                onClick = onEmpty,
                enabled = canEmpty,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (canEmpty) Color(0xFFF44336) else Color(0xFFBDBDBD),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = if (canEmpty) 4.dp else 1.dp
                )
            ) {
                Text(
                    text = "🗑️ Empty Bottle",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun SmoothCongratsDialog(
    onDismiss: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "dialog_scale"
    )
    
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🎉",
                    fontSize = 48.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                Text(
                    text = "Selamat!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1565C0),
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Botol air Anda sudah penuh!\n2L tercapai! 💧",
                    fontSize = 16.sp,
                    color = Color(0xFF666666),
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2196F3)
                    ),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = "Great! 🎊",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}
