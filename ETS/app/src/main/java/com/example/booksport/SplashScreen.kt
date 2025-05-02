package com.example.booksport

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import com.example.booksport.ui.theme.BabyJeans
import kotlinx.coroutines.delay
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.booksport.ui.theme.TextGray

@Composable
fun SplashScreen(navController: NavController) {
    var logoVisible by remember { mutableStateOf(false) }
    var textVisible by remember { mutableStateOf(false) }
    var allowClick by remember { mutableStateOf(false) }

    val scale = androidx.compose.animation.core.animateFloatAsState(
        targetValue = if (logoVisible) 1f else 0.5f,
        animationSpec = androidx.compose.animation.core.tween(durationMillis = 800),
        label = ""
    )

    LaunchedEffect(Unit) {
        logoVisible = true
        textVisible = true
        delay(1000)
        allowClick = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BabyJeans)
            .let {
                if (allowClick) {
                    it.clickable {
                        navController.navigate("home") {
                            popUpTo("splash") { inclusive = true }
                        }
                    }
                } else it
            },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AnimatedVisibility(visible = logoVisible) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo FieldSpot",
                    modifier = Modifier
                        .size(200.dp)
                        .scale(scale.value),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            AnimatedVisibility(visible = textVisible) {
                androidx.compose.material3.Text(
                    text = "click anywhere",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE0E0E0)
                )
            }
        }
    }
}