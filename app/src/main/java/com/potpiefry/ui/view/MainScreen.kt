package com.potpiefry.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
	val navController = rememberNavController()
	Scaffold(bottomBar = { BottomBar(navController = navController) }) { innerPadding ->
		BottomNavGraph(navController = navController, innerPadding)
	}
}

@Composable
fun BottomBar(navController: NavHostController) {
	val screens = listOf(
		BottomBarScreen.Home,
		BottomBarScreen.Settings
	)
	val navBackStackEntry by navController.currentBackStackEntryAsState()
	val currentDestination = navBackStackEntry?.destination

	NavigationBar() {
		screens.forEach { screen ->
			AddItem(
				screen = screen,
				currentDestination = currentDestination,
				navController = navController
			)
		}
	}
}


@Composable
fun RowScope.AddItem(
	screen: BottomBarScreen,
	currentDestination: NavDestination?,
	navController: NavHostController
) {
	NavigationBarItem(
		label = { Text(screen.title) },
		alwaysShowLabel = false,
		icon = {
			Icon(
				imageVector = screen.icon,
				contentDescription = "Navigation Icon"
			)
		},
		selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
		onClick = {
			navController.navigate(screen.route) {
				popUpTo(navController.graph.findStartDestination().id)
				launchSingleTop = true
			}
		})
}















