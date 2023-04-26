package com.potpiefry.ui.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

const val DETAIL_ARGUMENT_KEY = "id"

sealed class BottomBarScreen(
	val route: String,
	val title: String,
	val icon: ImageVector
) {
	object Home : BottomBarScreen("home", "Home", Icons.Default.Home)
	object Details :
		BottomBarScreen("details/{$DETAIL_ARGUMENT_KEY}", "Details", Icons.Default.Star) {
		fun passId(id: Int): String {
			return "details/$id"
		}
	}

	object Settings : BottomBarScreen("settings", "Settings", Icons.Default.Settings)
}