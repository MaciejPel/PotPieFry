package com.potpiefry.ui.view

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.potpiefry.ui.viewmodel.PreferencesViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph(
	navController: NavHostController,
	innerPadding: PaddingValues,
	preferencesViewModel: PreferencesViewModel = viewModel()
) {
	AnimatedNavHost(
		modifier = Modifier.padding(innerPadding),
		navController = navController,
		startDestination = BottomBarScreen.Home.route
	) {
		composable(
			route = BottomBarScreen.Home.route,
			enterTransition = {
				slideIntoContainer(
					AnimatedContentTransitionScope.SlideDirection.Right
				)
			},
			exitTransition = {
				slideOutOfContainer(
					AnimatedContentTransitionScope.SlideDirection.Left
				)
			},
		) {
			HomeScreen()
		}
		composable(
			route = BottomBarScreen.Settings.route,
			enterTransition = {
				when (initialState.destination.route) {
					"settings" -> EnterTransition.None
					else -> slideIntoContainer(
						AnimatedContentTransitionScope.SlideDirection.Left,
					)
				}
			},
			exitTransition = {
				when (initialState.destination.route) {
					"settings" -> ExitTransition.None
					else -> slideOutOfContainer(
						AnimatedContentTransitionScope.SlideDirection.Left,
					)
				}
			}
		) {
			SettingsScreen(preferencesViewModel)
		}
	}
}