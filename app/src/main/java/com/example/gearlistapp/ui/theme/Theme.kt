package com.example.gearlistapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = AppDarkGreen,
    secondary = AppDarkBrown,
    tertiary = AppLightGreen,
    background = AppBlack,
    surface = AppDarkGray,
    onPrimary = AppBeige,
    onSecondary = AppKhaki,
    onBackground = AppKhaki,
    onSurface = AppSand
)

private val LightColorScheme = lightColorScheme(
    primary = AppGreen,
    secondary = AppBrown,
    tertiary = AppKhaki,
    background = AppBeige,
    surface = White,
    onPrimary = AppBlack,
    onSecondary = AppDarkBrown,
    onBackground = AppBlack,
    onSurface = AppBlack
)
/**
 * Az alkalmazas szineit es tipografiat beallito komponens.
 */
@Composable
fun GearListAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}