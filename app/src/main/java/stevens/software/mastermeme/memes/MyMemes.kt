package stevens.software.mastermeme.memes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import stevens.software.mastermeme.R
import stevens.software.mastermeme.manropeFontFamily
import stevens.software.mastermeme.ui.theme.MasterMemeTheme

@Composable
fun MyMemesScreen(
    viewModel: MyMemesViewModel = koinViewModel()){

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    MyMemes(
        memeTemplates = uiState.value.memeTemplates
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyMemes(
    memeTemplates: List<Int>
) {
    val bottomSheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
               title = {
                   Text(
                       text = stringResource(R.string.my_memes_title),
                       color = colorResource(R.color.light_grey),
                       fontSize = 24.sp,
                       fontFamily = manropeFontFamily,
                       fontWeight = FontWeight.Medium,
                       modifier = Modifier.padding(16.dp)
                   )
               },
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = colorResource(R.color.dark_grey)
                )
            )
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

                    Box(modifier = Modifier
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
                            onClick = { showBottomSheet = true },
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

                    if(showBottomSheet) {
                        BottomSheetModal(
                            bottomSheetState = bottomSheetState,
                            onShowBottomSheet = { showBottomSheet = it },
                            memeTemplates = memeTemplates
                        )
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetModal(bottomSheetState: SheetState,
                     onShowBottomSheet: (Boolean) -> Unit,
                     memeTemplates: List<Int>){
    ModalBottomSheet(
        onDismissRequest = { onShowBottomSheet(false) },
        sheetState = bottomSheetState,
        containerColor = colorResource(R.color.dark_grey),
    ) {
        Text(
            text = stringResource(R.string.my_memes_bottom_sheet_title),
            fontSize = 16.sp,
            fontFamily = manropeFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(R.color.light_grey),
            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
        )
        Text(
            text = stringResource(R.string.my_memes_bottom_sheet_subtitle),
            fontSize = 12.sp,
            fontFamily = manropeFontFamily,
            fontWeight = FontWeight.Normal,
            color = colorResource(R.color.light_grey),
            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
        )
        Spacer(Modifier.size(42.dp))

        LazyVerticalGrid(
            columns = GridCells.Adaptive(176.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(memeTemplates) { drawable ->
                Box(
                    modifier = Modifier
                        .height(176.dp)
                        .background(Color.Black)
                        .clip(RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(drawable),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }

            }
        }
    }
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
        MyMemes(
            memeTemplates = listOf()
        )
    }
}

