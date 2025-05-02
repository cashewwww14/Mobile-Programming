package com.example.booksport

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.booksport.ui.theme.GreenPrimary
import com.example.booksport.ui.theme.WhiteBackground
import com.example.booksport.ui.theme.Poppins
import com.example.booksport.ui.theme.DarkGreen
import com.example.booksport.ui.theme.CreamBackground
import com.example.booksport.ui.theme.BlackText1
import com.example.booksport.ui.theme.TextGray


@Composable
fun LoginScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var usernameError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = CreamBackground // <- ganti jadi background cream
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // 🔼 Perbesar gambar ilustrasi
            Image(
                painter = painterResource(id = R.drawable.login_illustration),
                contentDescription = "Login Illustration",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Login",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = BlackText1
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = username,
                onValueChange = {
                    username = it
                    usernameError = false // reset error saat user mengetik
                },
                label = { Text("Username") },
                isError = usernameError,
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = "Username Icon")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                supportingText = {
                    if (usernameError) Text("Username tidak boleh kosong", color = MaterialTheme.colorScheme.error)
                }
            )


            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = false // reset error saat user mengetik
                },
                label = { Text("Password") },
                isError = passwordError,
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = "Password Icon")
                },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                supportingText = {
                    if (passwordError) Text("Password tidak boleh kosong", color = MaterialTheme.colorScheme.error)
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    usernameError = username.isBlank()
                    passwordError = password.isBlank()

                    if (!usernameError && !passwordError) {
                        navController.navigate("home")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DarkGreen),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(
                    text = "LOGIN",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Don't have an account? Sign up",
                fontSize = 14.sp,
                color = TextGray
            )

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "GoArena",
                fontSize = 45.sp, // 🔼 diperbesar
                fontWeight = FontWeight.Bold,
                fontFamily = Poppins,
                color = DarkGreen
            )
        }
    }
}
