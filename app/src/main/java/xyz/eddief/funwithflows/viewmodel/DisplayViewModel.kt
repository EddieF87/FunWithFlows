package xyz.eddief.funwithflows.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.eddief.funwithflows.DisplayUiState


abstract class DisplayViewModel2 : ViewModel() {
    open val vmTag: String = this.javaClass.simpleName
    val numberFlow = MutableStateFlow(1)
    val alphabetIndexFlow = MutableStateFlow(0)
    private val alphabetList: List<Char> = ('A'..'Z').toList()
    val alphabetFlow: Flow<Char> = alphabetIndexFlow.map {
        alphabetList[it % 26]
    }
    abstract val uiStateFlow: StateFlow<DisplayUiState>
    open fun updateNumber() = viewModelScope.launch {
        numberFlow.update { it + 1 }
    }

    open fun updateLetter() = viewModelScope.launch {
        alphabetIndexFlow.update { it + 1 }
    }
}