package com.example.foodiefriends.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
	private val dataStore: DataStore<Preferences>
) {
	companion object {
		private val ACCESS_TOKEN = stringPreferencesKey("accessToken")
	}

	suspend fun getAccessToken(): String {
		var token = ""
		withContext(Dispatchers.IO){
			try {
				dataStore.data.collect { preferences ->
					token = preferences[ACCESS_TOKEN] ?: ""
				}
			} catch (e: Exception){
				Log.e("UserRepoAccessToken", "Something Broke!: $e")
			}
		}

		return token
	}

	suspend fun setAccessToken(accessToken: String) {
		dataStore.edit { preferences ->
			preferences[ACCESS_TOKEN] = accessToken
		}
	}
}