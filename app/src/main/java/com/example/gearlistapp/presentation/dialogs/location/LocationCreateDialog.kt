package com.example.gearlistapp.presentation.dialogs.location

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.gearlistapp.R

/**
 * A helyszin letrehozasahoz szukseges dialogus.
 * @param onDismiss a dialogus bezarasahoz
 * @param onSave a helyszin elmentesehez
 */
@Composable
fun LocationCreateDialog(onDismiss: () -> Unit, onSave: (String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.add_new_location)) },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = {
                        name = it
                        isError = it.isBlank()            },
                    label = { Text(stringResource(id = R.string.name)) },
                    isError = isError,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                if (isError) {
                    Text(
                        text = stringResource(id = R.string.this_field_is_required),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        /** Mentes gomb */
        confirmButton = {
            TextButton(onClick = {
                isError = name.isBlank()
                if (!isError) {
                    onSave(name)
                }
            }) {
                Text(stringResource(id = R.string.save))
            }
        },
        /** Megse gomb */
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(id = R.string.cancel))
            }
        }
    )
}