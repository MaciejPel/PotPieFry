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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.potpiefry.ui.viewmodel.NavigationViewModel
import com.potpiefry.ui.viewmodel.SettingsViewModel
import com.potpiefry.ui.viewmodel.ThemeType
import com.potpiefry.util.DeviceType
import com.potpiefry.util.WindowSize
import com.potpiefry.util.WindowType
import com.potpiefry.util.getLabelTextSize
import com.potpiefry.util.getTitleTextSize

@Composable
fun SettingsScreen(
	settingsViewModel: SettingsViewModel = viewModel(),
	deviceType: DeviceType
) {
	val settingsUiState by settingsViewModel.uiState.collectAsState()
	val themeOptions =
		listOf(ThemeType.Light, ThemeType.Dark, ThemeType.System)

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
				text = "theme".uppercase(),
				fontSize = getLabelTextSize(deviceType),
				fontWeight = FontWeight.Bold
			)
		}
		themeOptions.forEach { option ->
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.selectable(
						selected = settingsUiState.theme == option,
						onClick = { settingsViewModel.selectTheme(option) },
						role = Role.RadioButton
					)
					.padding(16.dp),
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				Text(
					text = option.name,
					fontSize = getTitleTextSize(deviceType),
				)
				RadioButton(
					selected = settingsUiState.theme == option,
					onClick = null
				)
			}
		}
	}
}
