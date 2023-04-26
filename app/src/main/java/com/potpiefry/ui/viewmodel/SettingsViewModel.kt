package com.potpiefry.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SettingsUiState(
	val theme: Boolean? = null,
)

class SettingsViewModel : ViewModel() {
	private val _uiState = MutableStateFlow(SettingsUiState())
	val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

	init {
		resetSettings()
	}

	private fun resetSettings() {
		_uiState.value = SettingsUiState(null)
	}

	fun updateTheme(theme: Boolean? = null) {
		_uiState.update { currentState -> currentState.copy(theme = theme) }
	}

}