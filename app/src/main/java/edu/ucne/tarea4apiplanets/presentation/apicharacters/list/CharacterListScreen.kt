package edu.ucne.tarea4apiplanets.presentation.apicharacters.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.tarea4apiplanets.domain.apicharacters.model.Character
import edu.ucne.tarea4apiplanets.ui.theme.Tarea4ApiPlanetsTheme
import androidx.compose.foundation.clickable
import coil.compose.AsyncImage
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListScreen(
    viewModel: CharacterListViewModel = hiltViewModel(),
    onCharacterClick: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CharacterListBodyScreen(
        state = state,
        onEvent = viewModel::onEvent,
        onCharacterClick = onCharacterClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListBodyScreen(
    state: CharacterListUiState,
    onEvent: (CharacterListUiEvent) -> Unit,
    onCharacterClick: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Personajes Dragon Ball") }
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
                gender = state.filterGender,
                race = state.filterRace,
                onEvent = onEvent
            )

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            state.error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)
                )
            }

            LazyColumn(contentPadding = PaddingValues(16.dp)) {
                items(state.characters) { character ->
                    CharacterItem(
                        character = character,
                        onClick = { onCharacterClick(character.id) }
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
    gender: String,
    race: String,
    onEvent: (CharacterListUiEvent) -> Unit
) {
    ElevatedCard(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = name,
                onValueChange = { onEvent(CharacterListUiEvent.UpdateFilters(it, gender, race)) },
                label = { Text("Nombre (ej. Goku)") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = gender,
                    onValueChange = { onEvent(CharacterListUiEvent.UpdateFilters(name, it, race)) },
                    label = { Text("Género") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = race,
                    onValueChange = { onEvent(CharacterListUiEvent.UpdateFilters(name, gender, it)) },
                    label = { Text("Raza") },
                    modifier = Modifier.weight(1f)
                )
            }

            Button(onClick = { onEvent(CharacterListUiEvent.Search) }, modifier = Modifier.align(Alignment.End)) {
                Text("Buscar")
            }
        }
    }
}

@Composable
fun CharacterItem(
    character: Character,
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
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = character.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "${character.race} • ${character.gender}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterListBodyScreenPreview() {
    val sampleCharacters = listOf(
        Character(1, "Goku", "60.000.000", "Saiyan", "Male", "Protagonista", "", "90.000.000.000"),
        Character(2, "Vegeta", "50.000.000", "Saiyan", "Male", "Príncipe Saiyan", "", "80.000.000.000")
    )
    Tarea4ApiPlanetsTheme {
        Surface {
            CharacterListBodyScreen(
                state = CharacterListUiState(characters = sampleCharacters),
                onEvent = {},
                onCharacterClick = {}
            )
        }
    }
}