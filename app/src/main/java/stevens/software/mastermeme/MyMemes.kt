package stevens.software.mastermeme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stevens.software.mastermeme.ui.theme.MasterMemeTheme

@Composable
fun MyMemes() {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colorResource(R.color.dark_grey))
            ) {
                Text(
                    text = stringResource(R.string.my_memes_title),
                    color = colorResource(R.color.light_grey),
                    fontSize = 24.sp,
                    fontFamily = manropeFontFamily,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        },
        content = { contentPadding ->
            Box(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize()
                    .background(color = colorResource(R.color.dark_black))
            ) {
                Column {
                    EmptyState(modifier = Modifier.weight(1f))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 32.dp, end = 16.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        val brush = Brush.verticalGradient(
                            listOf(
                                colorResource(R.color.light_purple),
                                colorResource(R.color.dark_purple)
                            )
                        )

                        IconButton(
                            onClick = {},
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(brush)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.plus),
                                tint = Color.Black,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun EmptyState(modifier: Modifier) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.empty_state_icon),
                contentDescription = null
            )
            Spacer(Modifier.size(32.dp))
            Text(
                text = stringResource(R.string.my_memes_empty_state),
                color = colorResource(R.color.grey),
                fontSize = 12.sp,
                fontFamily = manropeFontFamily,
                fontWeight = FontWeight.Normal
            )
        }
    }

}


@Preview
@Composable
fun MyMemesPreview() {
    MasterMemeTheme {
        MyMemes()
    }
}

