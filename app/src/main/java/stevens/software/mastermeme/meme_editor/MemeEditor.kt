package stevens.software.mastermeme.meme_editor

import android.R.attr.textSize
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import androidx.appcompat.app.AlertDialog
import androidx.compose.animation.core.copy
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.height
import androidx.compose.ui.unit.size
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import stevens.software.mastermeme.common.Header
import stevens.software.mastermeme.R
import stevens.software.mastermeme.impactFontFamily
import stevens.software.mastermeme.manropeFontFamily
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.setValue
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.toSize
import androidx.core.content.res.ResourcesCompat
//import fontResource
//import fontFamily
import kotlin.div
import kotlin.math.roundToInt

@Composable
fun MemeEditorScreen(
    viewModel: MemeEditorViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    MemeEditor(
        memeTemplate = uiState.value.memeTemplate,
        textBox = uiState.value.textBox,
        navigateBack = onNavigateBack,
        onSaveMeme = {},
        onAddText = {
            viewModel.addTextBox()
        },
        onEditText = {
            viewModel.editText(it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemeEditor(
    memeTemplate: Int,
    textBox: TextBox?,
    navigateBack: () -> Unit,
    onSaveMeme: () -> Unit,
    onAddText: () -> Unit, 
    onEditText: (String) -> Unit
) {
    var openLeaveDialog by remember { mutableStateOf(false) }
    var openEditTextDialog by remember { mutableStateOf(false) }

    if (openLeaveDialog) {
        LeaveEditorDialog(
            onDismissDialog = { openLeaveDialog = false },
            onLeaveClicked = navigateBack
        )
    }

    if (openEditTextDialog) {
        EditTextDialog(
            text = textBox?.text ?: "",
            onDismissDialog = { openEditTextDialog = false },
            onOkayClicked = {
                openEditTextDialog = false
                onEditText(it)
            }
        )
    }
    Scaffold(
        topBar = {
            Header(
                text = stringResource(R.string.new_meme_title),
                backButton = {
                    BackButton(onBackClicked = {
                        openLeaveDialog = true
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
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(380.dp)
                        .padding(horizontal = 16.dp)
                        .weight(2f),
                    contentAlignment = Alignment.Center
                ) {


                    Image(
                        painter = painterResource(memeTemplate),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(380.dp)
                            .padding(horizontal = 16.dp)
//                            .weight(2f)
                    )

                    if (textBox?.text != null) {
                        val memeText: String = textBox.text
                        /*CanvasText(memeText)*/

                        MemeText(
                            memeText = memeText,
                            onTextClickedTwice = {
                                openEditTextDialog = true
                            }
                        )
                    }

                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colorResource(R.color.dark_grey))
                        .padding(16.dp)
                ) {
                    val brush = Brush.horizontalGradient(
                        listOf(
                            colorResource(R.color.light_purple),
                            colorResource(R.color.dark_purple)
                        )
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        OutlinedButton(
                            modifier = Modifier,
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, brush),
                            onClick = { onAddText() }
                        ) {
                            Text(
                                text = stringResource(R.string.add_text),
                                fontFamily = manropeFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp,
                                color = colorResource(R.color.light_purple)
                            )
                        }

                        Spacer(Modifier.size(18.dp))
                        Box(modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(brush)
                            .padding(horizontal = 16.dp)
                            .padding(vertical = 10.dp)
                            .clickable {
                                onSaveMeme()
                            }) {
                            Text(
                                text = stringResource(R.string.save_meme),
                                fontFamily = manropeFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp,
                                color = colorResource(R.color.very_dark_purple)
                            )
                        }

                    }
                }


            }

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
                    fontWeight = FontWeight.Normal
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTextDialog(
    text: String,
    onDismissDialog: () -> Unit,
    onOkayClicked: (String) -> Unit,
) {
    var memeText by remember {
        mutableStateOf(text)
    }
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
                    text = stringResource(R.string.edit_text_dialog_title),
                    color = colorResource(R.color.light_grey),
                    fontSize = 24.sp,
                    fontFamily = manropeFontFamily,
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.size(20.dp))

                TextField(
                    value = memeText,
                    onValueChange = {
                        memeText = it
                    },
                    modifier = Modifier.padding(horizontal = 16.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = colorResource(R.color.light_purple),
                        unfocusedIndicatorColor = colorResource(R.color.light_purple)

                    ),
                    textStyle = TextStyle.Default.copy(
                        color = colorResource(R.color.white),
                        fontFamily = manropeFontFamily,
                        fontSize = 14.sp
                    )
                )


                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(
                        onClick = onDismissDialog,
                    ) {
                        Text(
                            text = stringResource(R.string.edit_text_dialog_cancel),
                            color = colorResource(R.color.very_light_purple),
                            fontFamily = manropeFontFamily,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    TextButton(
                        onClick = {
                            onOkayClicked(memeText)
                        },
                    ) {
                        Text(
                            text = stringResource(R.string.edit_text_dialog_ok),
                            color = colorResource(R.color.very_light_purple),
                            fontFamily = manropeFontFamily,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

            }
        }
    }
}




@Preview
@Composable
fun MemeEditorPreview() {
    MemeEditor(
        memeTemplate = R.drawable.disaster_girl,
        navigateBack = {},
        onSaveMeme = {},
        onAddText = {},
        textBox = TextBox(text = "Double tap to edit"),
        onEditText = {}
    )
}

@Composable
fun MemeText(
    memeText: String,
    onTextClickedTwice: () -> Unit
){
    var count = 0
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    val style = TextStyle(
        fontFamily = impactFontFamily,
        fontSize = 40.sp,
        color = Color.White
    )

    Box(modifier = Modifier
        .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
        .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                change.consume()
                offsetX += dragAmount.x
                offsetY += dragAmount.y
            }
        }

    ) {
        Box(
            modifier = Modifier
                .border(
                    border = BorderStroke(1.dp, Color.White),
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(horizontal = 10.dp)
                .clickable{
                    count = count + 1
                    if(count == 2) {
                        onTextClickedTwice()
                    }
                }
        ) {
            Text(
                text = memeText,
                style = style,
            )

            Text(
                text = memeText,
                color = Color.Black,
                textDecoration = null,
                style = style.copy(
                    shadow = null,
                    drawStyle = Stroke(width = 3f),
                ),
            )
        }
    }


}

@Composable
fun CanvasText(text: String) {
    val memeText = text
    val impactTypeface = ResourcesCompat.getFont(LocalContext.current, R.font.impact)

    val boxRadius = with(LocalDensity.current) { 4.dp.toPx() }
    val fontSize = with(LocalDensity.current) { 35.sp.toPx() }
    val close = with(LocalDensity.current) { 10.dp.toPx() }

    val textPaintStroke = Paint().apply {
        isAntiAlias = true
        style = android.graphics.Paint.Style.STROKE
        textSize = fontSize
        color = android.graphics.Color.BLACK
        strokeWidth = 12f
        typeface = impactTypeface
    }

    val textPaint = Paint().apply {
        isAntiAlias = true
        style = android.graphics.Paint.Style.FILL
        textSize = fontSize
        color = android.graphics.Color.WHITE
        typeface = impactTypeface
    }

    val boxPaint = android.graphics.Paint().apply {
        color = Color.White.toArgb()
        style = android.graphics.Paint.Style.STROKE
        strokeWidth = 2f

    }

    val linePaint = android.graphics.Paint().apply {
        color = Color.White.toArgb()
        style = android.graphics.Paint.Style.STROKE
        strokeWidth = 4f

    }
    val colour = colorResource(R.color.red)

    val closeButton = android.graphics.Paint().apply {
        color = colour.toArgb()
        style = android.graphics.Paint.Style.FILL
    }

    Canvas(
        modifier = Modifier
            .fillMaxWidth(),
        onDraw = {
            drawIntoCanvas {
                val textBounds = android.graphics.Rect()
                textPaint.getTextBounds(memeText, 0, memeText.length, textBounds)

                val boxLeft = 0f
                val boxTop = 0f
                val boxRight = boxLeft + textBounds.width() + 70f
                val boxBottom = boxTop + textBounds.height() + 70f

                it.nativeCanvas.drawRoundRect(
                    boxLeft,
                    boxTop,
                    boxRight,
                    boxBottom,
                    boxRadius,
                    boxRadius,
                    boxPaint
                )


                it.nativeCanvas.drawCircle(
                    boxRight,
                    boxTop,
                    close,
                    closeButton
                )

                val centerX = boxRight
                val centerY = boxTop
                val radius = close
                val startX = centerX - radius / 2
                val startY = centerY + radius / 2
                val endX = centerX + radius / 2
                val endY = centerY - radius / 2

                it.nativeCanvas.drawLine(
                    startX,
                    startY,
                    endX,
                    endY,
                    linePaint
                )

                it.nativeCanvas.drawLine(
                    centerX + radius / 2,
                    centerY + radius / 2,
                    centerX - radius / 2,
                    centerY - radius / 2,
                    linePaint
                )

                it.nativeCanvas.drawText(
                    memeText,
                    boxLeft + 35f,
                    boxTop + textBounds.height() + 35f,
                    textPaintStroke
                )
                it.nativeCanvas.drawText(
                    memeText,
                    boxLeft + 35f,
                    boxTop + textBounds.height() + 35f,
                    textPaint
                )


            }
        }
    )
}


/*Canvas(
    modifier = Modifier
        .fillMaxWidth(),
    onDraw = {
        drawIntoCanvas {
            it.nativeCanvas.drawText(
                memeText,
                0f,
                120.dp.toPx(),
                textPaintStroke
            )
            it.nativeCanvas.drawText(
                memeText,
                0f,
                120.dp.toPx(),
                textPaint
            )
        }
    }
)*/