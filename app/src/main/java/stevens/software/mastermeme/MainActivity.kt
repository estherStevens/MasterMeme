package stevens.software.mastermeme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import stevens.software.mastermeme.di.appModule
import stevens.software.mastermeme.ui.theme.MasterMemeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
//        enableEdgeToEdge()
        startKoin {
            androidContext(this@MainActivity)
            modules(appModule)
        }
        setContent {
            MaterialTheme {
                MainNavController()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MaterialTheme {
        MainNavController()
    }
}

val manropeFontFamily = FontFamily(
    Font(resId = R.font.manrope_light, weight = FontWeight.Light),
    Font(resId = R.font.manrope_regular, weight = FontWeight.Normal),
    Font(resId = R.font.manrope_medium, weight = FontWeight.Medium),
    Font(resId = R.font.manrope_semibold, weight = FontWeight.SemiBold)
)