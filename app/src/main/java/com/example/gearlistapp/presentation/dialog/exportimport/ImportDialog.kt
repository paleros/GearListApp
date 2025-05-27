package com.example.gearlistapp.presentation.dialog.exportimport

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.gearlistapp.GearListApplication
import com.example.gearlistapp.R
import com.example.gearlistapp.data.database.AppDatabase
import java.io.FileOutputStream
import java.io.InputStream

/**
 * Import dialogus, amely lehetove teszi az adatbazis importalasat.
 * @param onDismiss A dialogus bezarasa.
 */
@Composable
fun ImportDialog(
    onDismiss: () -> Unit,
) {
    val context = LocalContext.current

    val importLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            try {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                val inputStream = context.contentResolver.openInputStream(uri)
                if (inputStream != null) {
                    MyImporter.importDatabaseFromStream(context, inputStream)
                    Toast.makeText(context, context.getString(R.string.import_successful), Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, context.getString(R.string.failed_to_open_file), Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, context.getString(R.string.import_failed) + ": ${e.message}", Toast.LENGTH_LONG).show()
            }
            onDismiss()
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(id = R.string.import_database)) },
        text = {
            Column {
                Text(stringResource(id = R.string.select_a_database_file))
                Text(
                    stringResource(R.string.import_overwrites_data_are_you_sure),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                importLauncher.launch(arrayOf("*/*"))
            }) {
                Text(stringResource(id = R.string.import_))
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text(stringResource(id = R.string.cancel))
            }
        }
    )
}

object MyImporter {
    /**
     * Beimportalja az adatbazist a megadott input streambol.
     * @param context Az alkalmazas contextje.
     * @param inputStream A beolvasando adatfolyam.
     */
    fun importDatabaseFromStream(context: Context, inputStream: InputStream) {
        val dbFile = context.getDatabasePath("gearlist_database")

        try {
            val dbField = GearListApplication::class.java.getDeclaredField("db")
            dbField.isAccessible = true
            val currentDb = dbField.get(null) as? AppDatabase
            currentDb?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        inputStream.use { input ->
            FileOutputStream(dbFile).use { output ->
                input.copyTo(output)
            }
        }
    }
}