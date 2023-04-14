package com.potpiefry.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun BottomNavGraph(navController: NavHostController, innerPadding: PaddingValues) {
	NavHost(navController = navController, startDestination = BottomBarScreen.Home.route) {
		composable(route = BottomBarScreen.Home.route) {
			HomeScreen(innerPadding)
		}
		composable(route = BottomBarScreen.Settings.route) {
			SettingsScreen(innerPadding)
		}
	}
}