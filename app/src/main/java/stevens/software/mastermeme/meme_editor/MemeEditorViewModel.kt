package stevens.software.mastermeme.meme_editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MemeEditorViewModel : ViewModel() {

    val _uiState = MutableStateFlow<MemeEditorUiState>(
        MemeEditorUiState(
            memeTemplate = 0
        )
    )

    val uiState = _uiState.asStateFlow()

    fun setMemeTemplate(memeTemplate: Int) {
        viewModelScope.launch {
            _uiState.emit(MemeEditorUiState(memeTemplate))
        }
    }
}


data class MemeEditorUiState(val memeTemplate: Int)