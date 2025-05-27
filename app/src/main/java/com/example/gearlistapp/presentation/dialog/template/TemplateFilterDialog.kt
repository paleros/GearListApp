package com.example.gearlistapp.presentation.dialog.template

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.gearlistapp.R
import kotlin.math.roundToInt

/**
 * A sablon szuro dialogus
 * @param onDismiss a dialogus bezarasa
 * @param onRangeSelected a szuro beallitas
 * @param previousMin az elozo minimum
 * @param previousMax az elozo maximum
 */
@Composable
fun TemplateFilterDialog(
    onDismiss: () -> Unit,
    onRangeSelected: (Int, Int) -> Unit,
    previousMin: Int = 1,
    previousMax: Int = 30
) {
    var sliderPosition by remember { mutableStateOf(previousMin.toFloat()..previousMax.toFloat()) }
    var endPosition by remember { mutableStateOf("") }
    endPosition = sliderPosition.endInclusive.roundToInt().toString()
    if (endPosition == "30")
        endPosition = "30+"

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.filter)) },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "${stringResource(R.string.duration_day)}: " +
                            "${sliderPosition.start.roundToInt()} - " +
                            endPosition
                )
                Spacer(modifier = Modifier.height(8.dp))
                RangeSlider(
                    value = sliderPosition,
                    onValueChange = { sliderPosition = it },
                    valueRange = 1f..30f,
                    steps = 28
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onRangeSelected(
                    sliderPosition.start.roundToInt(),
                    sliderPosition.endInclusive.roundToInt()
                )
                onDismiss()
            }) {
                Text(text = stringResource(id = R.string.use))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.cancel))
            }
        }
    )
}

/**
 * A sablon szuro dialogus elozetes megjelenitese
 */
@Preview
@Composable
fun TemplateFilterDialogPreview() {
    TemplateFilterDialog(
        onDismiss = {},
        onRangeSelected = { _, _ -> },
        previousMin = 1,
        previousMax = 30
    )
}