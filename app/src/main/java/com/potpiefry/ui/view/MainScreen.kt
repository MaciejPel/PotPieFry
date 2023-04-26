package com.potpiefry.ui.view

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.potpiefry.ui.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen(preferencesViewModel: SettingsViewModel = viewModel()) {
	val navController = rememberAnimatedNavController()
	val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

	Scaffold(
		modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
		topBar = {
			TopAppBar(
				scrollBehavior = scrollBehavior,
				title = { Text(text = "Title") },
				navigationIcon = {
					IconButton(onClick = { /*TODO*/ }) {
						Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
					}
				},
			)
		},
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
		icon = {
			Icon(
				imageVector = screen.icon,
				contentDescription = "Navigation Icon"
			)
		},
		selected = currentDestination?.hierarchy?.any {
			Log.d("S", "SELECTED")
			it.route == screen.route
		} == true,
		onClick = {
			Log.d("C", "CLICKED")
			navController.navigate(screen.route) {
				popUpTo(navController.graph.findStartDestination().id)
				launchSingleTop = true
			}
		})
}
















