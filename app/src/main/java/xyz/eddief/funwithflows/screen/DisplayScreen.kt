package xyz.eddief.funwithflows.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import xyz.eddief.funwithflows.DisplayUiState
import xyz.eddief.funwithflows.viewmodel.DisplayViewModel2

@Composable
fun DisplayScreen(
    viewModel: DisplayViewModel2,
    title: String
) {
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()
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
            when (val state = uiState) {
                is DisplayUiState.Content -> Text(text = state.displayText, fontSize = 64.sp)
                is DisplayUiState.Error -> {
                    Text(text = "Error ${state.errorText}", fontSize = 24.sp, color = Color.Red)
                }
                is DisplayUiState.Loading ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Text(text = state.loadingText, fontSize = 24.sp)
                    }
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(24.dp), modifier = Modifier.padding(vertical = 24.dp)) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                onClick = { viewModel.updateNumber() }
            ) {
                val number by viewModel.numberFlow.collectAsStateWithLifecycle()
                Text(text = "Number++ $number", fontSize = 36.sp)
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                onClick = { viewModel.updateLetter() }
            ) {
                val letter by viewModel.alphabetFlow.collectAsStateWithLifecycle('*')
                Text(text = "Letter++ $letter", fontSize = 36.sp)
            }
        }
    }
}