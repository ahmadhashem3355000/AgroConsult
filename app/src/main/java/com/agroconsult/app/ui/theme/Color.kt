package com.agroconsult.app.ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

private val PrimaryGreen = Color(0xFF2E7D32)
private val PrimaryGreenDark = Color(0xFF1B5E20)
private val PrimaryGreenLight = Color(0xFF81C784)
private val AccentOrange = Color(0xFFFF9800)
private val AccentOrangeDark = Color(0xFFF57C00)

private val White = Color(0xFFFFFFFF)
private val Black = Color(0xFF000000)
private val Gray50 = Color(0xFFFAFAFA)
private val Gray100 = Color(0xFFF5F5F5)
private val Gray900 = Color(0xFF212121)

val LightColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    onPrimary = White,
    primaryContainer = PrimaryGreenLight,
    onPrimaryContainer = Black,
    secondary = AccentOrange,
    onSecondary = White,
    secondaryContainer = AccentOrangeDark,
    onSecondaryContainer = White,
    tertiary = Color(0xFF6750a4),
    onTertiary = White,
    error = Color(0xFFb3261e),
    onError = White,
    errorContainer = Color(0xfff9dedc),
    onErrorContainer = Color(0xff410e0b),
    background = Gray50,
    onBackground = Gray900,
    surface = White,
    onSurface = Gray900,
    surfaceVariant = Gray100,
    onSurfaceVariant = Color(0xFF49454e),
    outline = Color(0xFF79747e),
    outlineVariant = Color(0xFFcac7d0),
    scrim = Black
)

val DarkColorScheme = darkColorScheme(
    primary = PrimaryGreenLight,
    onPrimary = PrimaryGreenDark,
    primaryContainer = PrimaryGreen,
    onPrimaryContainer = PrimaryGreenLight,
    secondary = AccentOrange,
    onSecondary = Black,
    secondaryContainer = AccentOrangeDark,
    onSecondaryContainer = White,
    tertiary = Color(0xFFd0bcff),
    onTertiary = Color(0xFF381e72),
    tertiaryContainer = Color(0xFF4f378b),
    onTertiaryContainer = Color(0xFFede7f6),
    error = Color(0xFFf2b8b5),
    onError = Color(0xFF601410),
    errorContainer = Color(0xFF8c1d18),
    onErrorContainer = Color(0xFff9dedc),
    background = Color(0xFF1c1b1f),
    onBackground = Gray100,
    surface = Color(0xFF1c1b1f),
    onSurface = Color(0xFFe6e1e6),
    surfaceVariant = Color(0xFF49454e),
    onSurfaceVariant = Color(0xFFcac7d0),
    outline = Color(0xFF94909b),
    outlineVariant = Color(0xFF49454e),
    scrim = Black
)
