package com.example.gearlistapp.ui.common

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.Year
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.gearlistapp.R
import java.time.Month

/**
 * Egy datum kivalasztasara hasznalatos legordulo menu
 * @param date a kezdeti datum
 * @param onYearSelected az ev kivalasztasa
 * @param onMonthSelected a honap kivalasztasa
 * @param onDaySelected a nap kivalasztasa
 */
@SuppressLint("DefaultLocale")
@Composable
fun SimpleDatePicker(
    date: String = "",
    onYearSelected: (String) -> Unit = {},
    onMonthSelected: (String) -> Unit = {},
    onDaySelected: (String) -> Unit = {},
) {
    val parts = date.split("-")
    var selectedYear by remember { mutableIntStateOf(parts[0].toInt()) }
    var selectedMonth by remember { mutableIntStateOf(parts[1].toInt()) }
    var selectedDay by remember { mutableIntStateOf(parts[2].toInt()) }

    Column(modifier = Modifier.padding(16.dp)) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            val thisYear = Year.now().value
            DropdownSelector(
                label = stringResource(R.string.year),
                options = (thisYear..thisYear+10).toList(),
                selected = selectedYear,
                onSelect = { selectedYear = it
                           onYearSelected(selectedYear.toString())},
                modifier = Modifier.weight(1f)
            )

            DropdownSelector(
                label = stringResource(R.string.month),
                options = (1..12).toList(),
                selected = selectedMonth,
                onSelect = { selectedMonth = it
                    onMonthSelected(String.format("%02d", selectedMonth))},
                modifier = Modifier.weight(1f)
            )

            val maxDay = Month.of(selectedMonth).length(Year.isLeap(selectedYear.toLong()))
            DropdownSelector(
                label = stringResource(R.string.Day),
                options = (1..maxDay).toList(),
                selected = selectedDay.coerceAtMost(maxDay),
                onSelect = { selectedDay = it
                    onDaySelected(String.format("%02d", selectedDay))},
                modifier = Modifier.weight(1f)
            )
        }
    }
}

/**
 * Egy legordulo menut megvalosito komponens
 * @param label a legordulo menu cime
 * @param options a legordulo menu opcioi
 * @param selected a kivalasztott opcio
 * @param onSelect a kivalasztott opcio
 * @param modifier a komponens modositasa
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DropdownSelector(
    label: String,
    options: List<T>,
    selected: T,
    onSelect: (T) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selected.toString(),
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryEditable, enabled = true),
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.exposedDropdownSize()
        ) {
            Column {
                options.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            onSelect(option)
                            expanded = false
                        },
                        text = { Text(option.toString()) },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun SimpleDatePickerPreview() {
    SimpleDatePicker("2000-01-01")
}