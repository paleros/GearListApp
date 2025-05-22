package com.example.gearlistapp.presentation.dialogs.actualtemplate

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gearlistapp.R
import com.example.gearlistapp.presentation.viewmodel.GearViewModel
import com.example.gearlistapp.presentation.viewmodel.TemplateViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Az aktualis sablont letrehozo dialogus
 * @param templateTitle a sablon neve
 * @param templateViewModel a sablon viewmodelje
 * @param gearViewModel a felszereles viewmodelje
 * @param onDismiss a dialogus bezarasa
 * @param onSave a sablon elmentese
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActualTemplateCreateDialog(
    templateTitle: String,
    templateViewModel: TemplateViewModel,
    gearViewModel: GearViewModel,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    val zoneId = ZoneId.systemDefault()
    val today = LocalDate.now().plusDays(1)
    val initialMillis = today.atStartOfDay(zoneId).toInstant().toEpochMilli()
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initialMillis)

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    DatePickerDialog(

        onDismissRequest = { },
        confirmButton = {
            TextButton(
                onClick = {
                    onSave(
                        datePickerState.selectedDateMillis?.let {
                            val date = Instant.ofEpochMilli(it)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                            date.format(formatter)
                        }.toString()
                    )
                }
            ) {
                Text(stringResource(id = R.string.save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(id = R.string.cancel))
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            showModeToggle = false,
            headline = null,
            title = {
                Column(modifier = Modifier.padding(top = 16.dp, start = 24.dp, end = 24.dp)) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        templateTitle,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )
    }
}