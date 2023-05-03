package com.potpiefry.ui.view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.potpiefry.ui.components.NavDrawer
import com.potpiefry.ui.viewmodel.HomeViewModel
import com.potpiefry.ui.viewmodel.NavigationViewModel
import com.potpiefry.ui.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen(settingsViewModel: SettingsViewModel = viewModel()) {
	val navController = rememberAnimatedNavController()
	val navigationViewModel: NavigationViewModel = viewModel()
	val navigationUiState by navigationViewModel.uiState.collectAsState()

	val homeViewModel: HomeViewModel = viewModel()
	val homeUiState by homeViewModel.uiState.collectAsState()

	val drawerState = rememberDrawerState(DrawerValue.Closed)
	val drawerScope = rememberCoroutineScope()

	val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

	NavDrawer(navController, navigationViewModel, drawerState, drawerScope) {
		Scaffold(
			modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
			topBar = {
				if (!navigationUiState.route.contains("detail")) {
					CenterAlignedTopAppBar(
						scrollBehavior = scrollBehavior,
						title = { Text(text = navigationUiState.title) },
						navigationIcon = {
							IconButton(onClick = {
								drawerScope.launch { drawerState.open() }
							}) {
								Icon(Icons.Default.Menu, "Menu")
							}
						},
					)
				}
			},
			bottomBar = {
				BottomAppBar(
					modifier = Modifier.imePadding(),
					actions = {
						when (navigationUiState.route) {
							NavigationScreen.Home.route -> {
								OutlinedTextField(
									modifier = Modifier
										.padding(horizontal = 12.dp)
										.fillMaxWidth(),
									shape = MaterialTheme.shapes.extraLarge,
									value = homeUiState.query,
									onValueChange = { homeViewModel.setQuery(it) },
									singleLine = true,
									maxLines = 1,
									leadingIcon = {
										Icon(Icons.Filled.Search, "Search")
									},
									trailingIcon = {
										if (homeUiState.query.isNotEmpty()) {
											IconButton(onClick = { homeViewModel.setQuery("") }) {
												Icon(Icons.Filled.Close, "Empty")
											}
										}
									}
								)
							}

							else -> {
								IconButton(onClick = {
									navController.popBackStack()
								}) {
									Icon(Icons.Filled.ArrowBack, "Back")
								}
							}
						}
					},
					floatingActionButton = {
						when (navigationUiState.title) {
							NavigationScreen.Home.title -> null
							NavigationScreen.Settings.title -> null
							else -> {
								ShareButton(navigationUiState.currentDish)
							}
						}
					}
				)
			}
		) { innerPadding ->
			NavGraph(
				navController = navController,
				navigationViewModel = navigationViewModel,
				innerPadding = innerPadding,
				homeViewModel = homeViewModel,
				settingsViewModel = settingsViewModel
			)
		}
	}
}




