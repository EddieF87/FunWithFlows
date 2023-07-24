package xyz.eddief.funwithflows.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.withContext
import xyz.eddief.funwithflows.DisplayUiState
import xyz.eddief.funwithflows.customErrorLogging
import xyz.eddief.funwithflows.customLogging

class FlowOnViewModel : DisplayViewModel2() {
    override val uiStateFlow: StateFlow<DisplayUiState> =
        flowOf(1..3).transform { numbers: IntRange ->
            kotlinx.coroutines.delay(1500)
            numbers.forEach {
//                withContext(Dispatchers.IO) {
                customLogging("1 $it")
                Thread.sleep(1000)
                customLogging("2 $it")
                emit(DisplayUiState.Loading())
                Thread.sleep(1000)
                customLogging("3 $it")
                emit(DisplayUiState.Content.BasicContent("$it"))
//                }
            }
        }
            .flowOn(Dispatchers.IO)
            .catch { t: Throwable ->
                customErrorLogging("${t.message}")
                emit(DisplayUiState.Error(t.message))
            }.onEach {
                customLogging("onEach $it")
            }
            .stateIn(viewModelScope, SharingStarted.Eagerly, DisplayUiState.Loading())
}