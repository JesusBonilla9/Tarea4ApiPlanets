package edu.ucne.tarea4apiplanets.presentation.apiplanets.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import edu.ucne.tarea4apiplanets.domain.apiplanets.model.Planet
import edu.ucne.tarea4apiplanets.ui.theme.Tarea4ApiPlanetsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetListScreen(
    viewModel: PlanetListViewModel = hiltViewModel(),
    onPlanetClick: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    PlanetListBodyScreen(
        state = state,
        onEvent = viewModel::onEvent,
        onPlanetClick = onPlanetClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetListBodyScreen(
    state: PlanetListUiState,
    onEvent: (PlanetListUiEvent) -> Unit,
    onPlanetClick: (Int) -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Planetas Dragon Ball") }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            FilterSection(
                name = state.filterName,
                isDestroyed = state.filterIsDestroyed,
                onEvent = onEvent,
            )

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp)
            ) {
                items(state.planets) { planet ->
                    PlanetItem(
                        planet = planet,
                        onClick = { onPlanetClick(planet.id) }
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}


@Composable
fun FilterSection(
    name: String,
    isDestroyed: Boolean?,
    onEvent: (PlanetListUiEvent) -> Unit,
) {

    ElevatedCard(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { onEvent(PlanetListUiEvent.UpdateFilters(it, isDestroyed)) },
                label = { Text("Nombre (ej. Tierra)") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { onEvent(PlanetListUiEvent.Search) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Buscar")
            }
        }
    }
}

@Composable
fun PlanetItem(
    planet: Planet,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = planet.image,
                contentDescription = planet.name,
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(planet.name)
                Text(
                    text = if (planet.isDestroyed) "Destruido" else "Intacto",
                    color = if (planet.isDestroyed) Color.Red else Color.Green
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlanetListBodyScreenPreview() {
    val samplePlanets = listOf(
        Planet(
            id = 1,
            name = "Tierra",
            isDestroyed = false,
            description = "Un planeta habitado por humanos y otros seres.",
            image = ""
        ),
        Planet(
            id = 2,
            name = "Namek",
            isDestroyed = true,
            description = "Hogar de los Namekianos.",
            image = ""
        )
    )
    val state = PlanetListUiState(
        planets = samplePlanets,
        filterName = "Tierra"
    )

    Tarea4ApiPlanetsTheme {
        Surface {
            PlanetListBodyScreen(
                state = state,
                onEvent = {},
                onPlanetClick = {}
            )
        }
    }
}