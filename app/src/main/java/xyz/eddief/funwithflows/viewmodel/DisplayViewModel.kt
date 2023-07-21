package xyz.eddief.funwithflows.viewmodel

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import xyz.eddief.funwithflows.DisplayUiState

interface DisplayViewModel {
    val randomChars
        get() = listOf('a', 'b', 'c', '#', '$', '%', '^', '!', '@', '&', '*')
    val uiStateFlow: StateFlow<DisplayUiState>
    fun updateNumber(): Job
    fun updateCharacter(): Job
}