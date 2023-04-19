package com.potpiefry.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class PreferencesUiState(
	val theme: Boolean? = null,
)

class PreferencesViewModel : ViewModel() {
	private val _uiState = MutableStateFlow(PreferencesUiState())
	val uiState: StateFlow<PreferencesUiState> = _uiState.asStateFlow()

	init {
		resetPreferences()
	}

	private fun resetPreferences() {
		_uiState.value = PreferencesUiState(null)
	}

	fun updateTheme(theme: Boolean? = null) {
		_uiState.update { currentState -> currentState.copy(theme = theme) }
	}

}