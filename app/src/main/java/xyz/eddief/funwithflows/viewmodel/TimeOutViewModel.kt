package xyz.eddief.funwithflows.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withTimeout
import xyz.eddief.funwithflows.DisplayUiState
import xyz.eddief.funwithflows.customErrorLogging
import xyz.eddief.funwithflows.customLogging

class TimeOutViewModel : DisplayViewModel2() {
    override val uiStateFlow: StateFlow<DisplayUiState> = combine(
        numberFlow,
        alphabetFlow
    ) { number, letter ->
        val startTime = System.currentTimeMillis()
        val delayTime = if (number > 8) 0L else number * 100L
        customLogging("combine: $number, $letter")
        withTimeout(500) {
            delay(delayTime)
            customLogging("post-delay after ${System.currentTimeMillis() - startTime}: $number, $letter")
            DisplayUiState.Content.NumberLetterContent(number.toString(), letter.toString())
        }
    }.retryWhen<DisplayUiState> { t: Throwable, attempt: Long ->
        customErrorLogging("$t ... attempt:$attempt")
        val retryAttemptsAllowed = 3
        val shouldRetry = t is TimeoutCancellationException && attempt < retryAttemptsAllowed
        if (shouldRetry) {
            (3 downTo 1).forEach {
                emit(
                    DisplayUiState.Error(
                        "${t.message}" +
                                "\nRetry attempts left = ${retryAttemptsAllowed - attempt}" +
                                "\nRetrying again in $it seconds"
                    )
                )
                delay(1000)
            }
        } else {
            emit(DisplayUiState.Error("No more retries. Go back and try again"))
        }
        customErrorLogging("retryWhen after delay - shouldRetry:$shouldRetry")
        shouldRetry
    }.onEach {
        customLogging("$it")
    }.stateIn(viewModelScope, SharingStarted.Eagerly, DisplayUiState.Loading())
}