package com.potpiefry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.potpiefry.ui.theme.PotPieFryTheme
import com.potpiefry.ui.view.MainScreen
import com.potpiefry.ui.viewmodel.SettingsViewModel

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		WindowCompat.setDecorFitsSystemWindows(window, false)

		setContent {
			val settingsViewModel: SettingsViewModel = viewModel(factory = SettingsViewModel.Factory)
			val settingsUiState by settingsViewModel.uiState.collectAsState()

			PotPieFryTheme(darkTheme = settingsUiState.theme.valueBool) {
				MainScreen()
			}
		}
	}
}
