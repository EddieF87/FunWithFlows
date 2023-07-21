package xyz.eddief.funwithflows

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import xyz.eddief.funwithflows.viewmodel.DisplayViewModel

@Composable
fun DisplayScreen(
    viewModel: DisplayViewModel,
    title: String
) {
    val uiState by viewModel.uiStateFlow.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(title, fontSize = 48.sp)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(1f)
        ) {
            when(val state = uiState) {
                is DisplayUiState.Content -> Text(text = state.displayText, fontSize = 48.sp)
                DisplayUiState.Error -> Text(text = "Error", fontSize = 48.sp)
                is DisplayUiState.Loading -> Text(text = "Loading ${state.count}-___", fontSize = 48.sp)
            }
        }
        Button(onClick = { viewModel.updateNumber() }) {
            Text(text = "Number++")
        }
        Button(onClick = { viewModel.updateCharacter() }) {
            Text(text = "Random Char")
        }
    }
}