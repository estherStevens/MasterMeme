package stevens.software.mastermeme.meme_editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import stevens.software.mastermeme.R

class MemeEditorViewModel : ViewModel() {

    private val memeTemplate = MutableStateFlow<Int>(R.drawable.spider_man_triple)
    private val memeTexts = MutableStateFlow<TextBox?>(null)

    val uiState = combine(
        memeTemplate,
        memeTexts
    ) { memeTemplate, memeTexts ->
        MemeEditorUiState(
            memeTemplate = memeTemplate,
            textBox = memeTexts
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        MemeEditorUiState(
            memeTemplate = R.drawable.spider_man_triple,
            textBox = null
        )
    )


    fun setMemeTemplate(meme: Int) {
        viewModelScope.launch {
            memeTemplate.emit(meme)
        }
    }

    fun addTextBox() {
        viewModelScope.launch {
            memeTexts.emit(
                TextBox(
                    text = "Tap Twice to Edit",
                    isFocused = true,
                    xPosition = 0f,
                    yPosition = 0f
                )
            )
        }
    }

    fun editText(newMemeText: String){
        viewModelScope.launch {
            memeTexts.update {
                it?.copy(
                    text = newMemeText
                )
            }

        }
    }
}


data class MemeEditorUiState(val memeTemplate: Int, val textBox: TextBox?)

data class TextBox(val text: String, val isFocused: Boolean, val xPosition: Float, val yPosition: Float)