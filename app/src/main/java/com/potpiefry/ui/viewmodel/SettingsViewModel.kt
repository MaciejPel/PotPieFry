package com.potpiefry.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.potpiefry.PotPieFryApp
import com.potpiefry.data.PreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class ThemeType(val valueInt: Int, val valueBool: Boolean?) {
	System(0, null),
	Dark(1, true),
	Light(2, false);

	companion object {
		private val map = ThemeType.values().associateBy(ThemeType::valueInt)
		fun fromInt(type: Int) = map[type]
	}
}

data class SettingsUiState(
	val theme: ThemeType = ThemeType.System,
)

class SettingsViewModel(
	private val preferencesRepository: PreferencesRepository
) : ViewModel() {
	val uiState: StateFlow<SettingsUiState> =
		preferencesRepository.isDarkTheme.map { isDarkTheme ->
			SettingsUiState((ThemeType.fromInt(isDarkTheme) ?: 0) as ThemeType)
		}.stateIn(
			scope = viewModelScope,
			started = SharingStarted.WhileSubscribed(5_000),
			initialValue = SettingsUiState()
		)

	fun selectTheme(theme: ThemeType) {
		viewModelScope.launch {
			preferencesRepository.saveThemePreference(theme.valueInt)
		}
	}

	companion object {
		val Factory: ViewModelProvider.Factory = viewModelFactory {
			initializer {
				val application = (this[APPLICATION_KEY] as PotPieFryApp)
				SettingsViewModel(application.preferencesRepository)
			}
		}
	}

}