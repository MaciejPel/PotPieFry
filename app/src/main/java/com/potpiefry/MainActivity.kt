package com.potpiefry

import android.os.Bundle
import android.view.WindowManager
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
			val preferencesViewModel: SettingsViewModel = viewModel()
			val preferencesUiState by preferencesViewModel.uiState.collectAsState()

			PotPieFryTheme(darkTheme = preferencesUiState.theme) {
				MainScreen()
			}
		}
	}
}
