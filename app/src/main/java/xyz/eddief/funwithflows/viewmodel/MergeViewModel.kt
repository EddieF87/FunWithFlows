package xyz.eddief.funwithflows.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn
import xyz.eddief.funwithflows.DisplayUiState

class MergeViewModel : DisplayViewModel2() {
    override val uiStateFlow: StateFlow<DisplayUiState> = merge(
        numberFlow.map(Int::toString),
        alphabetFlow.map(Char::toString)
    ).map {
        DisplayUiState.Content.BasicContent("Latest = $it")
    }.catch<DisplayUiState> {t: Throwable ->
        emit(DisplayUiState.Error(t.message))
    }.stateIn(viewModelScope, SharingStarted.Eagerly, DisplayUiState.Loading())
}