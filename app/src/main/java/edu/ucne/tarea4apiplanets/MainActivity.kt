package edu.ucne.tarea4apiplanets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.tarea4apiplanets.presentation.navigation.PlanetNavHost
import edu.ucne.tarea4apiplanets.ui.theme.Tarea4ApiPlanetsTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import edu.ucne.tarea4apiplanets.presentation.navigation.Screen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Tarea4ApiPlanetsTheme {
                val navController = rememberNavController()
                val items = listOf(
                    TopLevelRoute("Planetas", Screen.PlanetList, Icons.Default.Place),
                    TopLevelRoute("Personajes", Screen.CharacterList, Icons.Default.Face)
                )

                Scaffold(
                    bottomBar = {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination

                        val isDetail = currentDestination?.hasRoute(Screen.PlanetDetail::class) == true ||
                                currentDestination?.hasRoute(Screen.CharacterDetail::class) == true

                        if (!isDetail) {
                            NavigationBar {
                                items.forEach { item ->
                                    NavigationBarItem(
                                        icon = { Icon(item.icono, contentDescription = item.nombre) },
                                        label = { Text(item.nombre) },
                                        selected = currentDestination?.hierarchy?.any {
                                            it.hasRoute(item.ruta::class)
                                        } == true,
                                        onClick = {
                                            navController.navigate(item.ruta) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    PlanetNavHost(
                        navHostController = navController,
                        innerPadding = innerPadding
                    )
                }
            }
        }
    }
}

data class TopLevelRoute<T : Any>(
    val nombre: String,
    val ruta: T,
    val icono: ImageVector
)
