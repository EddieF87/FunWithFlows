package xyz.eddief.funwithflows

sealed class DisplayUiState {
    data class Loading(val loadingText: String = "Loading...") : DisplayUiState()
    data class Error(val errorText: String?) : DisplayUiState()
    sealed class Content(open val displayText: String) : DisplayUiState() {
        data class BasicContent(override val displayText: String) :
            Content(displayText)

        data class NumberLetterContent(val number: String, val letter: String) :
            Content("$number-$letter")
    }
}