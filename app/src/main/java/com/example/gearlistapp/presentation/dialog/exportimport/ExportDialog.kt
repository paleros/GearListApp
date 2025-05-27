package com.example.gearlistapp.presentation.dialog.exportimport

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.gearlistapp.R
import java.io.OutputStream

/**
 * Export dialogus, amely lehetove teszi az adatbazis exportalasat.
 * @param onDismiss A dialogus bezarasa.
 * @param exportLauncher Az ActivityResultLauncher, amely kezeli az exportalast.
 */
@Composable
fun ExportDialog(
    onDismiss: () -> Unit,
    exportLauncher: ActivityResultLauncher<Intent>
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(id = R.string.export_database)) },
        text = { Text(stringResource(id = R.string.select_a_directory)) },
        confirmButton = {
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or
                                Intent.FLAG_GRANT_WRITE_URI_PERMISSION or
                                Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
                    }
                    exportLauncher.launch(intent)
                    onDismiss()
                }
            ) {
                Text(stringResource(id = R.string.export))
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text(stringResource(id = R.string.cancel))
            }
        }
    )
}

object MyExporter {
    /**
     * Exportalja az adatbazist egy OutputStream-ra.
     * @param context Az alkalmazas Context-je.
     * @param outputStream Az OutputStream, ahova az adatbazist exportalni szeretnenk.
     */
    fun exportDatabaseToStream(context: Context, outputStream: OutputStream) {
        val dbFile = context.getDatabasePath("gearlist_database")
        dbFile.inputStream().use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
    }
}