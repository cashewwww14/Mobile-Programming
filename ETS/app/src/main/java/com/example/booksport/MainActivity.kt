package com.example.booksport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.booksport.ui.theme.BookSportTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookSportTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    GoArenaNavigation(navController)
                }
            }
        }
    }
}

@Composable
fun GoArenaNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") {
            SplashScreen(navController)
        }

        composable("home") {
            HomeScreen(navController)
        }

        composable(
            route = "select_time/{sport}/{imageResId}/{harga}",
            arguments = listOf(
                navArgument("sport") { type = NavType.StringType },
                navArgument("imageResId") { type = NavType.IntType },
                navArgument("harga") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val sport = backStackEntry.arguments?.getString("sport") ?: ""
            val imageResId = backStackEntry.arguments?.getInt("imageResId") ?: 0
            val harga = backStackEntry.arguments?.getInt("harga") ?: 0
            SelectTimeScreen(navController, sport, imageResId, harga)
        }

        composable(
            route = "booking_confirm/{sport}/{details}/{totalPrice}",
            arguments = listOf(
                navArgument("sport") { type = NavType.StringType },
                navArgument("details") { type = NavType.StringType },
                navArgument("totalPrice") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val sport = backStackEntry.arguments?.getString("sport") ?: ""
            val details = backStackEntry.arguments?.getString("details") ?: ""
            val totalPrice = backStackEntry.arguments?.getInt("totalPrice") ?: 0
            BookingConfirmScreen(navController, sport, details, totalPrice)
        }

        composable(
            route = "confirm/{name}/{sport}/{date}/{time}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("sport") { type = NavType.StringType },
                navArgument("date") { type = NavType.StringType },
                navArgument("time") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val sport = backStackEntry.arguments?.getString("sport") ?: ""
            val date = backStackEntry.arguments?.getString("date") ?: ""
            val time = backStackEntry.arguments?.getString("time") ?: ""
            ConfirmScreen(navController, name, sport, date, time)
        }

    }
}
