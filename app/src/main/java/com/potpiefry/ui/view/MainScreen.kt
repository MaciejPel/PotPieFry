package com.potpiefry.ui.view

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.potpiefry.ui.components.NavDrawer
import com.potpiefry.ui.viewmodel.DetailsViewModel
import com.potpiefry.ui.viewmodel.HomeViewModel
import com.potpiefry.ui.viewmodel.NavigationViewModel
import com.potpiefry.ui.viewmodel.SettingsViewModel
import com.potpiefry.util.getDisplayTextSize
import com.potpiefry.util.getHeadlineTextSize
import com.potpiefry.util.getIconSize
import com.potpiefry.util.getTitleTextSize
import com.potpiefry.util.getTitleTextStyle
import com.potpiefry.util.rememberDeviceType
import kotlinx.coroutines.launch

@SuppressLint("RememberReturnType", "CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen(settingsViewModel: SettingsViewModel = viewModel()) {
	val navController = rememberAnimatedNavController()
	val navigationViewModel: NavigationViewModel = viewModel()
	val navigationUiState by navigationViewModel.uiState.collectAsState()

	val homeViewModel: HomeViewModel = viewModel()
	val detailsViewModel: DetailsViewModel = viewModel()

	val drawerState = rememberDrawerState(DrawerValue.Closed)
	val drawerScope = rememberCoroutineScope()

	val deviceType = rememberDeviceType()

	val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

	val focusRequester = remember { FocusRequester() }
	val focusManager = LocalFocusManager.current

	val openDialog = remember { mutableStateOf(false) }
	var timerValue by remember { mutableStateOf("") }

	NavDrawer(navController, navigationViewModel, drawerState, drawerScope, deviceType) {
		Scaffold(
			modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
			topBar = {
				if (!navigationUiState.route.contains("detail")) {
					TopAppBar(
						scrollBehavior = scrollBehavior,
						title = {
							Text(
								text = navigationUiState.title,
								fontSize = getHeadlineTextSize(deviceType)
							)
						},
						navigationIcon = {
							IconButton(onClick = {
								drawerScope.launch { drawerState.open() }
							}) {
								Icon(
									Icons.Filled.Menu, "Menu", modifier = Modifier.size(getIconSize(deviceType))
								)
							}
						}
					)
				}
			},
			bottomBar = {
				BottomAppBar(
					modifier = Modifier.imePadding(),
					actions = {
						when (navigationUiState.route) {
							NavigationScreen.Home.route -> {
								if (!homeViewModel.loading && homeViewModel.errorMessage.isEmpty())
									OutlinedTextField(
										keyboardActions = KeyboardActions(onDone = {
											focusManager.clearFocus()
										}),
										modifier = Modifier
											.padding(horizontal = 12.dp)
											.fillMaxWidth()
											.focusRequester(focusRequester),
										shape = MaterialTheme.shapes.extraLarge,
										value = homeViewModel.query,
										onValueChange = { homeViewModel.setQuery(it) },
										singleLine = true,
										maxLines = 1,
										textStyle = getTitleTextStyle(deviceType),
										placeholder = {
											Text(text = "Search", fontSize = getTitleTextSize(deviceType))
										},
										leadingIcon = {
											Icon(
												Icons.Filled.Search,
												"Search",
												modifier = Modifier.size(getIconSize(deviceType))
											)
										},
										trailingIcon = {
											if (homeViewModel.query.isNotEmpty())
												IconButton(onClick = { homeViewModel.setQuery("") }) {
													Icon(
														Icons.Filled.Close,
														"Empty",
														modifier = Modifier.size(getIconSize(deviceType))
													)
												}
										}
									)
							}

							else -> {
								IconButton(onClick = {
									navController.popBackStack()
								}) {
									Icon(
										Icons.Filled.ArrowBack,
										"Back",
										modifier = Modifier.size(getIconSize(deviceType))
									)
								}
								if (navigationUiState.route.contains("detail") && detailsViewModel.dish != null) {
									IconButton(onClick = {
										openDialog.value = true
									}) {
										Icon(
											Icons.Filled.Add,
											"Add Timer",
											modifier = Modifier.size(getIconSize(deviceType))
										)
									}
								}
							}
						}
					},
					floatingActionButton = {
						if (navigationUiState.route.contains("detail") && detailsViewModel.dish != null) {
							ShareButton(detailsViewModel.dish!!, deviceType)
						}
					}
				)
			}
		) { innerPadding ->
			if (openDialog.value) {
				AlertDialog(
					onDismissRequest = { openDialog.value = false },
					title = { Text(text = "New timer", fontSize = getHeadlineTextSize(deviceType)) },
					text = {
						TextField(
							value = timerValue,
							onValueChange = { timerValue = it },
							modifier = Modifier.fillMaxWidth(),
							keyboardOptions = KeyboardOptions.Default.copy(
								imeAction = ImeAction.Done,
								keyboardType = KeyboardType.Number
							),
							maxLines = 1,
							singleLine = true,
							textStyle = getTitleTextStyle(deviceType),
							placeholder = { Text("Time in minutes", fontSize = getTitleTextSize(deviceType)) }
						)
					},
					confirmButton = {
						Button(
							onClick = {
								detailsViewModel.dish?.let {
									detailsViewModel.addTimer(
										it.id, (100..100_000_000).random(), timerValue.toInt() * 60
									)
								}
								timerValue = ""
								openDialog.value = false
							}) {
							Text("Add", fontSize = getTitleTextSize(deviceType))
						}
					},
					dismissButton = {
						Button(
							onClick = {
								timerValue = ""
								openDialog.value = false
							}) {
							Text("Cancel", fontSize = getTitleTextSize(deviceType))
						}
					}
				)
			}
			NavGraph(
				navController = navController,
				navigationViewModel = navigationViewModel,
				innerPadding = innerPadding,
				homeViewModel = homeViewModel,
				detailsViewModel = detailsViewModel,
				settingsViewModel = settingsViewModel,
				drawerState = drawerState,
				drawerScope = drawerScope,
				deviceType = deviceType
			)
		}
	}
}




