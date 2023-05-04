package com.potpiefry.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potpiefry.data.APIService
import com.potpiefry.data.DishPreview
import com.potpiefry.data.TabType
import kotlinx.coroutines.launch

val homeViewTabs = listOf(TabType.Start, TabType.Local, TabType.Abroad)

class HomeViewModel : ViewModel() {
	private val _dishList = mutableStateListOf<DishPreview>()
	var errorMessage: String by mutableStateOf("")

	private var _query: String by mutableStateOf("")

	val dishList: List<DishPreview>
		get() = _dishList
	val query: String
		get() = _query

	fun getDishList() {
		viewModelScope.launch {
			val apiService = APIService.getInstance()
			try {
				_dishList.clear()
				_dishList.addAll(apiService.getDishes())

			} catch (e: Exception) {
				errorMessage = e.message.toString()
			}
		}
	}

	fun setQuery(query: String) {
		_query = query
	}
}