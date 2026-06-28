package edu.ucne.tarea4apiplanets.presentation.apicharacters.detail
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import edu.ucne.tarea4apiplanets.domain.apicharacters.model.Character
import edu.ucne.tarea4apiplanets.ui.theme.Tarea4ApiPlanetsTheme

@Composable
fun DetailCharacterScreen(
    viewModel: DetailCharacterViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    DetailCharacterBodyScreen(state = state, onBack = onBack)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailCharacterBodyScreen(
    state: DetailCharacterUiState,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Personaje") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            state.error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center).padding(16.dp)
                )
            }

            state.character?.let { char ->
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    AsyncImage(
                        model = char.image,
                        contentDescription = char.name,
                        modifier = Modifier.fillMaxWidth().height(260.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = char.name, style = MaterialTheme.typography.headlineMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Raza: ${char.race}", style = MaterialTheme.typography.titleMedium)
                    Text(text = "Género: ${char.gender}", style = MaterialTheme.typography.titleMedium)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "Ki: ${char.ki}", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "Máx Ki: ${char.maxKi}", style = MaterialTheme.typography.bodyLarge)

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(text = "Descripción:", style = MaterialTheme.typography.titleSmall)
                    Text(text = char.description, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailCharacterScreenPreview() {
    val sampleCharacter = Character(
        id = 1,
        name = "Goku",
        ki = "60.000.000",
        race = "Saiyan",
        gender = "Male",
        description = "El principal protagonista de la serie.",
        image = "",
        maxKi = "90.000.000.000"
    )
    Tarea4ApiPlanetsTheme {
        Surface {
            DetailCharacterBodyScreen(
                state = DetailCharacterUiState(character = sampleCharacter),
                onBack = {}
            )
        }
    }
}