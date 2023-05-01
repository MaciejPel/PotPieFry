package com.potpiefry.ui.view

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.potpiefry.ui.viewmodel.HomeViewModel
import com.potpiefry.ui.viewmodel.NavigationViewModel
import com.potpiefry.ui.viewmodel.SettingsViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph(
	navController: NavHostController,
	navigationViewModel: NavigationViewModel = viewModel(),
	innerPadding: PaddingValues,
	homeViewModel: HomeViewModel = viewModel(),
	settingsViewModel: SettingsViewModel = viewModel()
) {
	AnimatedNavHost(
		modifier = Modifier.padding(innerPadding),
		navController = navController,
		startDestination = NavigationScreen.Home.route
	) {
		composable(
			route = NavigationScreen.Home.route,
			enterTransition = {
				slideIntoContainer(
					AnimatedContentTransitionScope.SlideDirection.Right,
					animationSpec = tween(400)
				)
			},
			exitTransition = {
				slideOutOfContainer(
					AnimatedContentTransitionScope.SlideDirection.Left,
					animationSpec = tween(400)
				)
			},
		) {
			HomeScreen(navController, navigationViewModel, homeViewModel)
		}
		composable(
			route = NavigationScreen.Details.route,
			arguments = listOf(navArgument("id") { type = NavType.IntType }),
			enterTransition = {
				slideIntoContainer(
					AnimatedContentTransitionScope.SlideDirection.Up,
					animationSpec = tween(400)
				)
			},
			exitTransition = {
				slideOutOfContainer(
					AnimatedContentTransitionScope.SlideDirection.Down,
					animationSpec = tween(400)
				)
			},
		) {
			val dishId = it.arguments?.getInt(DETAIL_ARGUMENT_KEY).toString().toInt()
			DetailsScreen(navController, navigationViewModel, dishId)
		}
		composable(
			route = NavigationScreen.Settings.route,
			enterTransition = {
				when (initialState.destination.route) {
					"settings" -> EnterTransition.None
					else -> slideIntoContainer(
						AnimatedContentTransitionScope.SlideDirection.Left,
						animationSpec = tween(400)
					)
				}
			},
			exitTransition = {
				when (initialState.destination.route) {
					"settings" -> ExitTransition.None
					else -> slideOutOfContainer(
						AnimatedContentTransitionScope.SlideDirection.Left,
						animationSpec = tween(400)
					)
				}
			}
		) {
			SettingsScreen(navigationViewModel, settingsViewModel)
		}
	}
}