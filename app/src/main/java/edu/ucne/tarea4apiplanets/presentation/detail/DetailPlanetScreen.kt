package edu.ucne.tarea4apiplanets.presentation.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import edu.ucne.tarea4apiplanets.domain.model.Planet

@Composable
fun DetailPlanetScreen(
    viewModel: DetailPlanetViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    DetailPlanetBodyScreen(
        state = state,
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPlanetBodyScreen(
    state: DetailPlanetUiState,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Planeta") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.planet?.let { planet ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                AsyncImage(
                    model = planet.image,
                    contentDescription = planet.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = planet.name,
                    style = MaterialTheme.typography.headlineMedium
                )

                Text(
                    text = if (planet.isDestroyed) "Estado: Destruido" else "Estado: Intacto",
                    style = MaterialTheme.typography.titleMedium,
                    color = if (planet.isDestroyed) Color.Red else Color.Green
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Descripción:",
                    style = MaterialTheme.typography.titleSmall
                )

                Text(
                    text = planet.description,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailPlanetBodyScreenPreview() {
    val samplePlanet = Planet(
        id = 1,
        name = "Tierra",
        isDestroyed = false,
        description = "Un planeta habitado por humanos y otros seres. Hogar de los guerreros Z.",
        image = "https://dragonball-api.com/api/planetas/Tierra.png"
    )

    val state = DetailPlanetUiState(
        isLoading = false,
        planet = samplePlanet,
        error = null
    )

    MaterialTheme {
        Surface {
            DetailPlanetBodyScreen(
                state = state,
                onBack = {}
            )
        }
    }
}