package com.example.foodiefriends.ui.login

import androidx.lifecycle.ViewModel
import com.example.foodiefriends.data.AuthRepository
import com.example.foodiefriends.ui.dashboard.DashboardDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
	private val authRepository: AuthRepository
) : ViewModel() {

	var errors: List<String> = emptyList()
	suspend fun login(email: String, password: String, openAndPopUp: (String, String) -> Unit) {
		val result = authRepository.loginUser(email, password)
		if (result?.isSuccessful == true) {
			openAndPopUp(DashboardDestination.route, LoginDestination.route)
		} else{
			result?.body()?.let {
				errors = it.errors
			}
		}
	}

	suspend fun signup(
		email: String,
		password: String,
		openAndPopUp: (String, String) -> Unit
	) {
		val result = authRepository.signUpUser(email, password)
		if (result?.isSuccessful == true) {
			openAndPopUp(DashboardDestination.route, SignupDestination.route)
		} else {
			result?.body()?.let {
				errors = it.error!!
			}
		}
	}

	fun onLoginClick(openAndPopUp: (String, String) -> Unit){
		openAndPopUp(LoginDestination.route,SignupDestination.route)
	}

	fun onSignUpClick(openAndPopUp: (String, String) -> Unit){
		openAndPopUp(SignupDestination.route,LoginDestination.route)
	}
}