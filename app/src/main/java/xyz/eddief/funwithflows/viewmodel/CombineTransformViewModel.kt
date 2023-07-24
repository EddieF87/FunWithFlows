package xyz.eddief.funwithflows.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import xyz.eddief.funwithflows.DisplayUiState
import xyz.eddief.funwithflows.customErrorLogging
import xyz.eddief.funwithflows.customLogging

class CombineTransformViewModel : DisplayViewModel2() {

//    override val uiStateFlow: StateFlow<DisplayUiState> = combineTransform(
//        numberFlow,
//        alphabetFlow.map { it.toString() }
//    ) { number: Int, char: String ->
//        customLogging("$number, $char")
//        if (number > 4) {
//            emit(DisplayUiState.Loading())
//            val numberString = number.toString()
//            (0..10).forEach {
//                emit(DisplayUiState.Content.NumberLetterContent("$numberString.$it", char))
//                delay(2000)
//            }
//        }
//    }.catch { t: Throwable ->
//        customErrorLogging("${t.message}")
//        emit(DisplayUiState.Error(t.message))
//    }.stateIn(viewModelScope, SharingStarted.Eagerly, DisplayUiState.Loading())


    override val uiStateFlow: StateFlow<DisplayUiState> = combine(
        numberFlow,
        alphabetFlow.map { it.toString() }
    ) { number: Int, char: String ->
        customLogging("combine $number, $char")
        if (number > 4) {
            val numberString = number.toString()
            DisplayUiState.Content.NumberLetterContent("$numberString", char)
        } else {
            DisplayUiState.Loading()
        }
    }.filterIsInstance<DisplayUiState.Content.NumberLetterContent>()
        .flatMapLatest { content ->
            customLogging("flatMapLatest $content")
            flow {
                (0..10).forEach {
                    emit(
                        DisplayUiState.Content.NumberLetterContent(
                            "${content.number}.$it", content.letter
                        )
                    )
                    delay(1000)
                }
            }
        }.catch<DisplayUiState> { t: Throwable ->
            customErrorLogging("${t.message}")
            emit(DisplayUiState.Error(t.message))
        }.stateIn(viewModelScope, SharingStarted.Eagerly, DisplayUiState.Loading())

}