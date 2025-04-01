package com.example.gearlistapp.ui.model

import android.content.Context
import androidx.annotation.StringRes
import com.example.gearlistapp.R

/**
 * A szoveg megjeleniteset reprezentalo osztaly.
 */
sealed class UiText {
    data class DynamicString(val value: String): UiText()
    data class StringResource(@StringRes val id: Int): UiText()

    fun asString(context: Context): String {
        return when(this) {
            is DynamicString -> this.value
            is StringResource -> context.getString(this.id)
        }
    }
}

/**
 * A Throwable osztaly kiterjesztese, amely lehetove teszi a hiba uzenet konvertalasat UiText-tel.
 * @receiver a Throwable osztaly.
 * @return a hiba uzenet UiText formaban.
 */
fun Throwable.toUiText(): UiText {
    val message = this.message.orEmpty()
    return if (message.isBlank()) {
        UiText.StringResource(R.string.some_error_message)
    } else {
        UiText.DynamicString(message)
    }
}