package com.example.gearlistapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.documentfile.provider.DocumentFile
import com.example.gearlistapp.presentation.dialogs.exportimport.MyExporter
import com.example.gearlistapp.presentation.dialogs.exportimport.MyImporter
import com.example.gearlistapp.presentation.screens.MainScreen
import com.example.gearlistapp.ui.theme.GearListAppTheme

/**
 * ---------------------------------------------------------------------------------------------------------------------
 * Created by peros on 2025.01.28.
 *
 * Budapesti Muszaki es Gazdasagtudomanyi Egyetem (BME)
 * Villamosmernoki es Informatikai Kar (VIK)
 * Automatizálási és Alkalmazott Informatikai Tanszék (AUT)
 * Onallo laboratorium (MSc): Utazast tamogato alkalmazas fejlesztase Android platformra
 *
 * Felhasznalt anyagok: stackoverflow.com, GitHub Copilot, ChatGTP, Gemini, developer.android.com,
 *                      slack-chats.kotlinlang.org, m2.material.io, fonts.google.com
 *                      Androidalapu szoftverfejlesztes, Mobil es webes szoftverek tantargy anyagai
 *
 * @author Eros Pal
 * @consulant Gazdi Laszlo
 * @since 2025.03.19.
 * ---------------------------------------------------------------------------------------------------------------------
 */

/**
 * MainActivity class
 */
class MainActivity : ComponentActivity() {

    private lateinit var exportLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exportLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val uri = result.data?.data
            if (result.resultCode == RESULT_OK && uri != null) {
                contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )

                val pickedDir = DocumentFile.fromTreeUri(this, uri)

                pickedDir?.findFile("gearlist_export.db")?.delete()
                val newFile = pickedDir?.createFile("application/octet-stream", "gearlist_export")

                if (newFile != null) {
                    val outputStream = contentResolver.openOutputStream(newFile.uri)
                    if (outputStream != null) {
                        MyExporter.exportDatabaseToStream(this, outputStream)
                        Toast.makeText(this, this.getString(R.string.export_successful) , Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, this.getString(R.string.failed_to_open_file), Toast.LENGTH_LONG)
                            .show()
                    }
                } else {
                    Toast.makeText(this, this.getString(R.string.failed_to_create_file), Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

        enableEdgeToEdge()
        setContent {
            GearListAppTheme {
                MainScreen(
                    exportLauncher = exportLauncher,
                )
            }
        }
    }
}