package com.potpiefry.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potpiefry.data.APIService
import com.potpiefry.data.Dish
import kotlinx.coroutines.launch
import java.util.Timer
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class Current(
  val id: Int,
  val index: Int,
  val timer: Timer
)

class DetailsViewModel : ViewModel() {
  private var _dish: Dish? by mutableStateOf(null)
  var errorMessage: String by mutableStateOf("")
  private var _timer: Current? by mutableStateOf(null)
  private var _remaining: Duration? by mutableStateOf(null)


  val timer: Current?
    get() = _timer

  val remaining: Duration?
    get() = _remaining

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
    if (_timer != null || _remaining != null) {
      _timer!!.timer.cancel()
      _timer = null
      _remaining = null
    }

    var base = h.hours + m.minutes + s.seconds
    val t = fixedRateTimer(initialDelay = 1000L, period = 1000L) {
      base = base.minus(1.seconds)
      if (base.inWholeSeconds == 0L) {
        _timer!!.timer.cancel()
        _remaining = null
        _timer = null
      } else {
        _remaining = base
      }
    }
    _timer = Current(id, index, t)
  }


}