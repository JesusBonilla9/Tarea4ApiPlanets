package edu.ucne.tarea4apiplanets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.tarea4apiplanets.presentation.navigation.PlanetNavHost
import edu.ucne.tarea4apiplanets.ui.theme.Tarea4ApiPlanetsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Tarea4ApiPlanetsTheme {
                val navController = rememberNavController()
                PlanetNavHost(navHostController = navController)
            }
        }
    }
}
