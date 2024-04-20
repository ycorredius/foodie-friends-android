package com.example.foodiefriends.ui.settings

import androidx.lifecycle.ViewModel
import com.example.foodiefriends.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
	private val authRepository: AuthRepository
) : ViewModel() {
	fun logout() {
		authRepository.logout()
	}
}