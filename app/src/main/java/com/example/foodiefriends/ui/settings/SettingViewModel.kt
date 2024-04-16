package com.example.foodiefriends.ui.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.foodiefriends.Key
import com.example.foodiefriends.data.AuthRepository
import com.example.foodiefriends.sharedPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
	private val authRepository: AuthRepository
) : ViewModel() {
	fun logout() {
		authRepository.logout()
	}
}