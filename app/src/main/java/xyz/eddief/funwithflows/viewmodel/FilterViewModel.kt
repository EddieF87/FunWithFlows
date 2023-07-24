package xyz.eddief.funwithflows.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.stateIn
import xyz.eddief.funwithflows.DisplayUiState

class FilterViewModel : DisplayViewModel2() {
    override val uiStateFlow: StateFlow<DisplayUiState> =
        numberFlow.filter { it % 2 == 0 }
            .combine(
                alphabetFlow.filterNot(::isProhibitedLetter)
            ) { number, letter ->
                DisplayUiState.Content.NumberLetterContent(number.toString(), letter.toString())
            }.stateIn(viewModelScope, SharingStarted.Eagerly, DisplayUiState.Loading())

    private fun isProhibitedLetter(char: Char) = when (char) {
        'F', 'G', 'H', 'J', 'K' -> true
        else -> false
    }
}