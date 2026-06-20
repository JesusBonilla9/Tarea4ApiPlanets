package edu.ucne.tarea4apiplanets.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.tarea4apiplanets.presentation.detail.DetailPlanetScreen
import edu.ucne.tarea4apiplanets.presentation.list.PlanetListScreen

@Composable
fun PlanetNavHost(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.PlanetList
    ) {
        composable<Screen.PlanetList> {
            PlanetListScreen { planetId ->
                navHostController.navigate(Screen.PlanetDetail(planetId))
            }
        }

        composable<Screen.PlanetDetail> {
            DetailPlanetScreen(
                onBack = {
                    navHostController.navigateUp()
                }
            )
        }
    }
}
