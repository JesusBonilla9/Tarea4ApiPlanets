package edu.ucne.tarea4apiplanets.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.tarea4apiplanets.presentation.apicharacters.detail.DetailCharacterScreen
import edu.ucne.tarea4apiplanets.presentation.apicharacters.list.CharacterListScreen
import edu.ucne.tarea4apiplanets.presentation.apiplanets.detail.DetailPlanetScreen
import edu.ucne.tarea4apiplanets.presentation.apiplanets.list.PlanetListScreen

@Composable
fun PlanetNavHost(
    navHostController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.PlanetList,
        modifier = Modifier.padding(innerPadding)
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
        composable<Screen.CharacterList> {
            CharacterListScreen { characterId ->
                navHostController.navigate(Screen.CharacterDetail(characterId))
            }
        }

        composable<Screen.CharacterDetail> {
            DetailCharacterScreen(
                onBack = {
                    navHostController.navigateUp()
                }
            )
        }
    }
}
