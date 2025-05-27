package com.example.gearlistapp.presentation.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.gearlistapp.R

/**
 * A torlest megerosito dialoogus komponens.
 * @param onConfirm a torles megerositese
 * @param onDismiss a dialoogus bezarasa
 */
@Composable
fun DeleteConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(id = R.string.want_delete)) },
        text = { Text(stringResource(id = R.string.cannot_be_undone)) },
        confirmButton = {
            Button(onClick = { onConfirm() }) {
                Text(stringResource(id = R.string.delete))
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss ) {
                Text(stringResource(id = R.string.cancel))
            }
        }
    )
}
