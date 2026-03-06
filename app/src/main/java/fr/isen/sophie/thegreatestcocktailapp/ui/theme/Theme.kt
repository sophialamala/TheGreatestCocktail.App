package fr.isen.sophie.thegreatestcocktailapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext


private val LightColorScheme = lightColorScheme(
    primary = SoftRose,
    onPrimary = Color.White,

    secondary = SoftOrange,
    onSecondary = Color.White,

    background = CreamBackground,
    onBackground = TextDark,

    surface = SoftSurface,
    onSurface = TextDark
)

@Composable
fun TheGreatestCocktailAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}

