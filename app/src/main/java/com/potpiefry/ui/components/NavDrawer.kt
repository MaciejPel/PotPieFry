package com.potpiefry.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.potpiefry.R
import com.potpiefry.ui.theme.shapeScheme
import com.potpiefry.ui.view.NavigationScreen
import com.potpiefry.ui.viewmodel.NavigationViewModel
import com.potpiefry.util.DeviceType
import com.potpiefry.util.WindowSize
import com.potpiefry.util.WindowType
import com.potpiefry.util.getHeadlineTextSize
import com.potpiefry.util.getIconSize
import com.potpiefry.util.getTitleTextSize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavDrawer(
	navController: NavController,
	navigationViewModel: NavigationViewModel,
	drawerState: DrawerState,
	scope: CoroutineScope,
	deviceType: DeviceType,
	content: @Composable () -> Unit
) {
	val navigationUiState by navigationViewModel.uiState.collectAsState()
	val screens = listOf(
		NavigationScreen.Home,
		NavigationScreen.Settings
	)

	ModalNavigationDrawer(
		gesturesEnabled = true,
		drawerState = drawerState,
		drawerContent = {
			ModalDrawerSheet(
				modifier = Modifier.width(300.dp),
				drawerShape = MaterialTheme.shapeScheme.none
			) {
				Text(
					text = stringResource(id = R.string.app_name),
					fontSize = getHeadlineTextSize(deviceType),
					modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 12.dp)
				)
				Divider()
				Column(
					modifier = Modifier
						.fillMaxSize()
						.padding(vertical = 12.dp),
					verticalArrangement = Arrangement.SpaceBetween
				) {
					screens.forEach { screen ->
						NavigationDrawerItem(
							icon = {
								Icon(
									screen.icon,
									contentDescription = null,
									modifier = Modifier.size(getIconSize(deviceType))
								)
							},
							label = {
								Text(screen.title, fontSize = getTitleTextSize(deviceType))
							},
							selected = screen.route == navigationUiState.route,
							onClick = {
								scope.launch { drawerState.close() }
								navController.navigate(screen.route) {
									popUpTo(navController.graph.findStartDestination().id) {
										saveState = true
									}
									launchSingleTop = true
									restoreState = true
								}
							},
							modifier = Modifier.padding(start = 0.dp, end = 12.dp),
							shape = MaterialTheme.shapeScheme.leftZeroRightFull
						)
					}
				}
			}
		},
		content = content
	)
}