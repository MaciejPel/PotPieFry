package com.potpiefry.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potpiefry.data.APIService
import com.potpiefry.data.Dish
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

data class TimerData(val id: Int, val index: Int, var value: Int)

class DetailsViewModel : ViewModel() {
	private var _dish: Dish? by mutableStateOf(null)
	var errorMessage: String by mutableStateOf("")
	var loading: Boolean by mutableStateOf(false)

	val timerListLiveData: LiveData<SnapshotStateList<TimerData>>
		get() = _timerList
	private val _timerList = MutableLiveData<SnapshotStateList<TimerData>>()
	private val timerListImpl = mutableListOf<TimerData>()
	private val timers = mutableListOf<Timer>()

	val dish: Dish?
		get() = _dish

	fun getDish(id: Int) {
		viewModelScope.launch {
			loading = true
			val apiService = APIService.getInstance()
			try {
				_dish = null
				_dish = apiService.getDish(id)
				errorMessage = ""
				loading = false

			} catch (e: Exception) {
				errorMessage = e.message.toString()
				loading = false
			}
		}
	}


	fun addTimer(id: Int, index: Int, value: Int) {
		val newTimerData = TimerData(id, index, value)
		timerListImpl.add(newTimerData)
		_timerList.value = timerListImpl.toMutableStateList()

		val timer = Timer()
		timer.scheduleAtFixedRate(object : TimerTask() {
			override fun run() {
				newTimerData.value--
				updateTimerList()
				if (newTimerData.value < 0) clearTimer()
			}
		}, 0, 1000)
		timers.add(timer)
	}

	private fun updateTimerList() {
		_timerList.postValue(timerListImpl.toMutableStateList())
	}

	fun clearTimer(id: Int? = null, index: Int? = null) {
		val timerIndex =
			timerListImpl.indexOfFirst { (it.id == id && it.index == index) || it.value <= 0 }
		if (timerIndex != -1) {
			timers[timerIndex].cancel()
			timers.removeAt(timerIndex)
			timerListImpl.removeAt(timerIndex)
			updateTimerList()
		}
	}

	override fun onCleared() {
		super.onCleared()
		timers.forEach { it.cancel() }
	}
}