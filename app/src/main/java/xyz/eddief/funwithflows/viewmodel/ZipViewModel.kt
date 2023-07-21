package xyz.eddief.funwithflows.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.eddief.funwithflows.DisplayUiState

class ZipViewModel : ViewModel(), DisplayViewModel {

    private val numberFlow = MutableStateFlow(0)
    private val characterFlow = MutableStateFlow<Char?>(null)

    override val uiStateFlow: StateFlow<DisplayUiState> = merge(
        numberFlow.map { "$it" },
        characterFlow.filterNotNull().map { it.toString() }
    ).map {
        DisplayUiState.Content(count = it, randomChar = it)
    }.catch {
        DisplayUiState.Error
    }.stateIn(viewModelScope, SharingStarted.Eagerly, DisplayUiState.Loading(0))

    override fun updateNumber() = viewModelScope.launch {
        numberFlow.update { it + 1 }
    }

    override fun updateCharacter() = viewModelScope.launch {
        val randomChar = randomChars.random()
        characterFlow.emit(randomChar)
    }
}