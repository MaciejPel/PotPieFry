package com.potpiefry.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.potpiefry.data.Dish
import com.potpiefry.data.TabType
import com.potpiefry.data.dishes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class HomeUiState(
	val dishes: List<Dish> = emptyList(),
	val query: String = "",
)

val tabs = listOf(TabType.Start, TabType.Local, TabType.Abroad)

class HomeViewModel : ViewModel() {
	private val _uiState = MutableStateFlow(HomeUiState())
	val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

	init {
		resetHome()
	}

	private fun resetHome() {
		_uiState.update { currentState -> currentState.copy(dishes = dishes) }
	}

	fun setQuery(query: String) {
		_uiState.update { currentState -> currentState.copy(query = query) }
	}
}