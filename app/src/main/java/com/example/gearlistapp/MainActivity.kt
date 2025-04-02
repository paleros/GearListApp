package com.example.gearlistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GearListAppTheme {
                MainScreen()
            }
        }
    }
}