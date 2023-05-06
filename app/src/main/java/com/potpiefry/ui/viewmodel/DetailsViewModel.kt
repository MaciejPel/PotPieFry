package com.potpiefry.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potpiefry.data.APIService
import com.potpiefry.data.Dish
import kotlinx.coroutines.launch
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class Timer(
	val id: Int,
	val index: Int,
	var remaining: Duration
)

class DetailsViewModel : ViewModel() {
	private var _dish: Dish? by mutableStateOf(null)
	var errorMessage: String by mutableStateOf("")
	private var _timer: Timer? by mutableStateOf(null)


	val timer: Timer?
		get() = _timer

	val dish: Dish?
		get() = _dish

	fun getDish(id: Int) {
		viewModelScope.launch {
			val apiService = APIService.getInstance()
			try {
				_dish = null
				_dish = apiService.getDish(id)
				errorMessage = ""

			} catch (e: Exception) {
				errorMessage = e.message.toString()
			}
		}
	}

	fun createTimer(
		id: Int,
		index: Int,
		h: Int,
		m: Int,
		s: Int,
	) {
		val duration = h.hours + m.minutes + s.seconds
		_timer = Timer(id, index, duration)
		launchTimer()
	}

	private fun launchTimer() {
		fixedRateTimer(initialDelay = 1000L, period = 1000L) {
			_timer =
				Timer(_timer!!.id, _timer!!.index, _timer?.remaining?.minus(1.seconds) ?: 0.seconds)
		}
	}
}