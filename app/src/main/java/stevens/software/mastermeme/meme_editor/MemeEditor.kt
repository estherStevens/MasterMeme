package stevens.software.mastermeme.meme_editor

import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import stevens.software.mastermeme.common.Header
import stevens.software.mastermeme.R
import stevens.software.mastermeme.manropeFontFamily

@Composable
fun MemeEditorScreen(viewModel: MemeEditorViewModel,
                     onNavigateBack: () -> Unit){
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    MemeEditor(
        memeTemplate = uiState.value.memeTemplate,
        navigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemeEditor(
    memeTemplate: Int,
    navigateBack: () -> Unit
) {
    var openDialog by remember { mutableStateOf(false) }

    if(openDialog) {
        LeaveEditorDialog(
            onDismissDialog = { openDialog = false },
            onLeaveClicked = navigateBack
        )
    }
    Scaffold(
        topBar = {
            Header(
                text = stringResource(R.string.new_meme_title),
                backButton = {
                    BackButton(onBackClicked = {
                        openDialog = true
                    })
                }
            )
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
                .background(colorResource(R.color.dark_black)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(memeTemplate),
                contentDescription = null,
                modifier = Modifier
                    .width(380.dp)
                    .height(380.dp)
                    .padding(horizontal = 16.dp)
            )
        }

    }
}

@Composable
fun BackButton(onBackClicked: () -> Unit) {
    Icon(
        painter = painterResource(R.drawable.back),
        contentDescription = null,
        tint = Color.Unspecified,
        modifier = Modifier
            .padding(16.dp)
            .clickable { onBackClicked() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaveEditorDialog(
    onDismissDialog: () -> Unit,
    onLeaveClicked: () -> Unit,
) {
    BasicAlertDialog(
        onDismissRequest = {
            onDismissDialog()
        }
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            color = colorResource(R.color.medium_grey)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(R.string.leave_meme_editor_dialog_title),
                    color = colorResource(R.color.light_grey),
                    fontSize = 24.sp,
                    fontFamily = manropeFontFamily,
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.size(20.dp))
                Text(
                    text = stringResource(R.string.leave_meme_editor_dialog_subtitle),
                    color = colorResource(R.color.light_grey),
                    fontSize = 16.sp,
                    fontFamily = manropeFontFamily,
                    fontWeight = FontWeight.Normal)
                Spacer(modifier = Modifier.height(24.dp))
                Row(horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()) {
                    TextButton(
                        onClick = onDismissDialog,
                    ) {
                        Text(
                            text = stringResource(R.string.leave_meme_editor_dialog_cancel_button),
                            color = colorResource(R.color.very_light_purple),
                            fontFamily = manropeFontFamily,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    TextButton(
                        onClick = onLeaveClicked,
                    ) {
                        Text(
                            text = stringResource(R.string.leave_meme_editor_dialog_leave_button),
                            color = colorResource(R.color.very_light_purple),
                            fontFamily = manropeFontFamily,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

            }
        }
    }
//    BasicAlertDialog(
//        onDismissRequest = {},
//    ){
//        Column {
//            Text(
//                text = stringResource(R.string.leave_meme_editor_dialog_title),
//                color = colorResource(R.color.light_grey),
//                fontSize = 24.sp,
//                fontFamily = manropeFontFamily,
//                fontWeight = FontWeight.Medium
//            )
//            Spacer(Modifier.size(20.dp))
//            Text(
//                text = stringResource(R.string.leave_meme_editor_dialog_subtitle),
//                color = colorResource(R.color.light_grey),
//                fontSize = 16.sp,
//                fontFamily = manropeFontFamily,
//                fontWeight = FontWeight.Normal)
//        }
//
//    }
}

@Preview
@Composable
fun MemeEditorPreview(){
    LeaveEditorDialog({}, {})
//    MemeEditor(
//        memeTemplate = R.drawable.disaster_girl,
//        onBackClicked = {},
//    )
}