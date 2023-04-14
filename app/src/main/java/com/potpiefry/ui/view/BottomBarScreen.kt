package com.potpiefry.ui.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
	val route: String,
	val title: String,
	val icon: ImageVector
) {
	object Home : BottomBarScreen("home", "Home", Icons.Default.Home)
	object Settings : BottomBarScreen("settings", "Settings", Icons.Default.Settings)
}