package com.potpiefry

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.potpiefry.data.PreferencesRepository


private const val THEME_PREFERENCE = "theme_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
	name = THEME_PREFERENCE
)

class PotPieFryApp : Application() {
	lateinit var preferencesRepository: PreferencesRepository

	override fun onCreate() {
		super.onCreate()
		preferencesRepository = PreferencesRepository(dataStore)
	}
}