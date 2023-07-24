package xyz.eddief.funwithflows.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.runningReduce
import kotlinx.coroutines.flow.stateIn
import xyz.eddief.funwithflows.DisplayUiState

class RunningReduceViewModel : DisplayViewModel2() {
    override val uiStateFlow: StateFlow<DisplayUiState> =
        numberFlow.runningReduce { accumulator, value ->
            val newValue = accumulator + value
            newValue
        }.map {
            DisplayUiState.Content.BasicContent(displayText = "$it")
        }.stateIn(viewModelScope, SharingStarted.Eagerly, DisplayUiState.Loading())
}