package xyz.eddief.funwithflows.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.transformLatest
import xyz.eddief.funwithflows.DisplayUiState
import xyz.eddief.funwithflows.customErrorLogging
import xyz.eddief.funwithflows.customLogging

class CombineViewModel : DisplayViewModel2() {
    override val uiStateFlow: StateFlow<DisplayUiState> = combine(
        numberFlow.map { it.toString() },
        alphabetFlow.map { it.toString() }
    ) { number: String, char: String ->
        DisplayUiState.Content.NumberLetterContent(number, char)
    }.catch<DisplayUiState> {t: Throwable ->
        customErrorLogging("${t.message}")
        emit(DisplayUiState.Error(t.message))
    }.onEach {
        customLogging("$it")
    }.stateIn(viewModelScope, SharingStarted.Eagerly, DisplayUiState.Loading())
}