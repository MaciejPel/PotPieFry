package com.potpiefry.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potpiefry.data.APIService
import com.potpiefry.data.DishPreview
import kotlinx.coroutines.launch

enum class TabType(val title: String, val value: String?) {
	Start("Main View", null),
	Local("Local", "local"),
	Abroad("Abroad", "abroad")
}

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
				errorMessage = ""

			} catch (e: Exception) {
				errorMessage = e.message.toString()
			}
		}
	}

	fun setQuery(query: String) {
		_query = query
	}
}