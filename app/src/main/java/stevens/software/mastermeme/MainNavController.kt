package stevens.software.mastermeme

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import stevens.software.mastermeme.memes.MyMemes
import stevens.software.mastermeme.memes.MyMemesScreen

@Serializable
object Memes

@Composable
fun MainNavController() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Memes) {
      composable<Memes>{
          MyMemesScreen()
      }
    }
}