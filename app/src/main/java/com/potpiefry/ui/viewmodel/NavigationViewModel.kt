package com.potpiefry.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.potpiefry.ui.view.NavigationScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class NavigationUiState(
	val name: String = "",
	val currentDish: Int? = null
)

class NavigationViewModel : ViewModel() {
	private val _uiState = MutableStateFlow(NavigationUiState())
	val uiState: StateFlow<NavigationUiState> = _uiState.asStateFlow()

	init {
		resetNavigation()
	}

	private fun resetNavigation() {
		_uiState.update { currentState ->
			currentState.copy(
				name = NavigationScreen.Home.title,
				currentDish = null
			)
		}
	}

	fun setNavigation(name: String) {
		_uiState.update { currentState -> currentState.copy(name = name) }
	}

	fun setDish(dishId: Int) {
		_uiState.update { currentState -> currentState.copy(currentDish = dishId) }
	}
}