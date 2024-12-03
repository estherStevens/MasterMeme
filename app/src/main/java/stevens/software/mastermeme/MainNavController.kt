package stevens.software.mastermeme

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import stevens.software.mastermeme.meme_editor.MemeEditor
import stevens.software.mastermeme.meme_editor.MemeEditorScreen
import stevens.software.mastermeme.meme_editor.MemeEditorViewModel
import stevens.software.mastermeme.memes.MyMemesScreen
import stevens.software.mastermeme.memes.MyMemesViewModel

@Serializable
object Memes

@Serializable
data class NewMeme(val meme: Int)

@Composable
fun MainNavController() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Memes) {
        composable<Memes> {
            MyMemesScreen(
                onMemeSelected = {
                    navController.navigate(
                        NewMeme(meme = it)
                    )
                }
            )
        }
        composable<NewMeme> { backStackEntry ->
            val viewModel: MemeEditorViewModel = koinViewModel()
            val meme = backStackEntry.arguments?.getInt("meme")
            viewModel.setMemeTemplate(meme ?: 0)
            MemeEditorScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}