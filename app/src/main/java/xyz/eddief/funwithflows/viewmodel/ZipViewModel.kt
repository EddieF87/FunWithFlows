package xyz.eddief.funwithflows.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.flow.zip
import xyz.eddief.funwithflows.DisplayUiState
import xyz.eddief.funwithflows.customErrorLogging
import xyz.eddief.funwithflows.customLogging

class ZipViewModel : DisplayViewModel2() {

    override val uiStateFlow: StateFlow<DisplayUiState> =
        numberFlow
            .onEach { customLogging("numberFlow $it") }
            .buffer(capacity = UNLIMITED)
            .zip(
                alphabetFlow
                    .onEach { customLogging("alphabetFlow $it") }
                    .buffer(capacity = UNLIMITED)
            ) { number: Int, letter: Char ->
                customLogging("$number  $letter")
                DisplayUiState.Content.NumberLetterContent(number.toString(), letter.toString())
            }.catch<DisplayUiState> { t: Throwable ->
                customErrorLogging("$t")
                emit(DisplayUiState.Error(t.message))
            }.onEach {
                customLogging("onEach $it")

            }
            .stateIn(viewModelScope, SharingStarted.Eagerly, DisplayUiState.Loading())


}