package com.potpiefry.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.potpiefry.ui.view.NavigationScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class NavigationUiState(
	val title: String = "",
	val route: String = "",
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
				title = NavigationScreen.Home.title,
				route = NavigationScreen.Home.route,
				currentDish = null
			)
		}
	}

	fun setNavigation(title: String, route: String) {
		_uiState.update { currentState -> currentState.copy(title = title, route = route) }
	}

	fun setDish(dishId: Int) {
		_uiState.update { currentState -> currentState.copy(currentDish = dishId) }
	}
}