package stevens.software.mastermeme.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stevens.software.mastermeme.R
import stevens.software.mastermeme.manropeFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
    text: String,
    backButton: @Composable (() -> Unit) = {},
) {
    TopAppBar(
        title = {
            Text(
                text = text,
                color = colorResource(R.color.light_grey),
                fontSize = 24.sp,
                fontFamily = manropeFontFamily,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(16.dp)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = colorResource(R.color.dark_grey)
        ),
        navigationIcon = {
            backButton()
        }
    )
}