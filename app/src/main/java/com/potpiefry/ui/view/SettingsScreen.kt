package com.potpiefry.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.potpiefry.ui.viewmodel.NavigationViewModel
import com.potpiefry.ui.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
	navigationViewModel: NavigationViewModel,
	settingsViewModel: SettingsViewModel = viewModel()
) {
	navigationViewModel.setNavigation(
		NavigationScreen.Settings.title,
		NavigationScreen.Settings.route
	)
	val settingsUiState by settingsViewModel.uiState.collectAsState()
	val themeOptions =
		listOf(Pair("Light", false), Pair("Dark", true), Pair("Automatic (system)", null))

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(vertical = 16.dp)
			.selectableGroup(),
		verticalArrangement = Arrangement.Top,
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 16.dp)
		) {
			Text(
				text = "THEME",
				fontSize = MaterialTheme.typography.bodySmall.fontSize,
				fontWeight = MaterialTheme.typography.labelLarge.fontWeight,
				color = MaterialTheme.colorScheme.secondary
			)
		}
		themeOptions.forEach { option ->
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.selectable(
						selected = settingsUiState.theme == option.second,
						onClick = { settingsViewModel.updateTheme(option.second) },
						role = Role.RadioButton
					)
					.padding(16.dp),
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				Text(text = option.first)
				RadioButton(
					selected = settingsUiState.theme == option.second,
					onClick = null
				)
			}
		}
	}
}
