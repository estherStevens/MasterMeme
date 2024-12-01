package stevens.software.mastermeme.memes

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class MyMemesViewModel(
    memesRepository: MemesRepository
) : ViewModel() {

    val uiState = MutableStateFlow<MyMemesUiState>(
        MyMemesUiState(memesRepository.memes)
    )
}

data class MyMemesUiState(val memeTemplates: List<Int>)