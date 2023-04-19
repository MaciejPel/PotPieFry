package com.potpiefry.ui.view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.potpiefry.ui.viewmodel.PreferencesViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen(preferencesViewModel: PreferencesViewModel = viewModel()) {
	val navController = rememberAnimatedNavController()
	Scaffold(
		bottomBar = { BottomBar(navController = navController) }
	) { innerPadding ->
		NavGraph(navController = navController, innerPadding, preferencesViewModel)
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
















