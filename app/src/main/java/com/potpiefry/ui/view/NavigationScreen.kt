package com.potpiefry.ui.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

const val DETAIL_ARGUMENT_KEY = "id"

sealed class NavigationScreen(
	val route: String,
	val title: String,
	val icon: ImageVector
) {
	object Home :
		NavigationScreen("home", "Home", Icons.Filled.Home)

	object Details :
		NavigationScreen("details/{$DETAIL_ARGUMENT_KEY}", "Details", Icons.Filled.Star) {
		fun passId(id: Int): String {
			return "details/$id"
		}
	}

	object Settings :
		NavigationScreen("settings", "Settings", Icons.Filled.Settings)
}