package com.potpiefry.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.potpiefry.ui.theme.shapeScheme
import com.potpiefry.ui.view.NavigationScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavDrawer(
	navController: NavController,
	drawerState: DrawerState,
	scope: CoroutineScope,
	content: @Composable () -> Unit
) {
	val screens = listOf(
		NavigationScreen.Home,
		NavigationScreen.Settings
	)
	val selectedItem = remember { mutableStateOf(screens[0]) }
	ModalNavigationDrawer(
		gesturesEnabled = true,
		drawerState = drawerState,
		drawerContent = {
			ModalDrawerSheet(
				modifier = Modifier.width(300.dp),
				drawerShape = MaterialTheme.shapeScheme.none
			) {
				Text(
					text = "PotPieFry",
					fontSize = MaterialTheme.typography.headlineSmall.fontSize,
					fontWeight = FontWeight.SemiBold,
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
							icon = { Icon(screen.icon, contentDescription = null) },
							label = { Text(screen.title) },
							selected = screen == selectedItem.value,
							onClick = {
								scope.launch { drawerState.close() }
								navController.navigate(screen.route) {
									popUpTo(navController.graph.findStartDestination().id)
									launchSingleTop = true
								}
								selectedItem.value = screen
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