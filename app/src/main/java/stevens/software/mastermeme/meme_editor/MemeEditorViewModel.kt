package stevens.software.mastermeme.meme_editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MemeEditorViewModel : ViewModel() {

    val _uiState = MutableStateFlow<MemeEditorUiState>(
        MemeEditorUiState(
            memeTemplate = 0,
            textBox = null
        )
    )

    val uiState = _uiState.asStateFlow()

    fun setMemeTemplate(memeTemplate: Int) {
        viewModelScope.launch {
            _uiState.emit(MemeEditorUiState(memeTemplate = memeTemplate, textBox = null))
        }
    }

    fun addTextBox() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    textBox = TextBox("Tap Twice to Edit")
                )


            }

           val i =  _uiState.value.textBox
            val o = i
        }
    }
}


data class MemeEditorUiState(val memeTemplate: Int, val textBox: TextBox?)

data class TextBox(val text: String)