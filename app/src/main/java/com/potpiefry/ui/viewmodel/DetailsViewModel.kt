package com.potpiefry.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potpiefry.data.APIService
import com.potpiefry.data.Dish
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {
	var errorMessage: String by mutableStateOf("")
	private var _dish: Dish? by mutableStateOf(null)

	val dish: Dish?
		get() = _dish

	fun getDish(id: Int) {
		viewModelScope.launch {
			val apiService = APIService.getInstance()
			try {
				_dish = null
				Log.d("X", id.toString())
				_dish = apiService.getDish(id)

			} catch (e: Exception) {
				errorMessage = e.message.toString()
			}
		}
	}
}