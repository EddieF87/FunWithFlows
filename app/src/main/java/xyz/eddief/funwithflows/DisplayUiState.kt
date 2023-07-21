package xyz.eddief.funwithflows

sealed class DisplayUiState {
    data class Loading(val count: Int) : DisplayUiState()
    object Error : DisplayUiState()
    data class Content(val count: String, val randomChar: String) : DisplayUiState() {
        val displayText = "$count-$randomChar"
    }
}