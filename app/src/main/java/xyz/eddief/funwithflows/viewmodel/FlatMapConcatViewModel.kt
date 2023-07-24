package xyz.eddief.funwithflows.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.transformLatest
import xyz.eddief.funwithflows.DisplayUiState
import xyz.eddief.funwithflows.customErrorLogging
import xyz.eddief.funwithflows.customLogging

class FlatMapConcatViewModel : DisplayViewModel2() {
    override val uiStateFlow: StateFlow<DisplayUiState> = numberFlow.flatMapConcat { number: Int ->
        customLogging("$number")
        alphabetFlow
            .take(1)
            .map(Char::toString)
            .transformLatest {
                customLogging("emit loading $it with $number")
                emit(DisplayUiState.Loading("Loading: ($number $it)"))
                delay(3000)
                customLogging("map $it with $number")
                emit(DisplayUiState.Content.NumberLetterContent(number.toString(), it))
            }
    }.catch<DisplayUiState> { t: Throwable ->
        customErrorLogging("${t.message}")
        emit(DisplayUiState.Error(t.message))
    }.onEach {
        customLogging("onEach $it")
    }.stateIn(viewModelScope, SharingStarted.Eagerly, DisplayUiState.Loading())
}