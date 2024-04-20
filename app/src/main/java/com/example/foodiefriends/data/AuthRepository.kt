package com.example.foodiefriends.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.foodiefriends.Key
import com.example.foodiefriends.network.AuthService
import com.example.foodiefriends.printMsg
import com.example.foodiefriends.putString
import com.example.foodiefriends.sharedPrefs
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
	private val authService: AuthService,
	@ApplicationContext context: Context,
	private val sharedPrefs: SharedPreferences = sharedPrefs(context)
) {
	// TODO: Figure out how throw a proper error
	suspend fun loginUser(email: String, password: String): Response<UserResponse>? {
		return try {
			val response = authService.loginUser(Auth(email, password))
			if (response.isSuccessful) {
				response.body()?.let {
					sharedPrefs.putString(Key.accessToken, it.token)
					it.user.data.attributes.avatar?.let { avatarUrl ->
						sharedPrefs.putString(
							Key.userPhoto,
							avatarUrl
						)
					}
					sharedPrefs.putString(Key.userName, it.user.data.attributes.name)
					sharedPrefs.putString(Key.userEmail, it.user.data.attributes.email)
				}
			} else {
				printMsg("@@@@ Something went wrong with the request: $response.")
			}
			response
		} catch (e: HttpException) {
			Log.e("AuthRepoLoginError", "Something went wrong trying to login", e)
			//TODO: Return an actual error response and not null
			null
		}
	}

	// TODO: Figure out how throw a proper error
	suspend fun signUpUser(email: String, password: String): Response<JWT>? {
		return try {
			val response = authService.signUpUser(Auth(email, password))
			if (response.isSuccessful) {
				response.body()?.token?.let {
					response.body()?.token?.let { sharedPrefs.putString(Key.accessToken, it) }
				}
			}
			response
		} catch (e: HttpException) {
			Log.e("AuthRepoLoginError", "Something went wrong trying to login", e)
			null
		}
	}

	fun logout() {
		sharedPrefs.putString(Key.accessToken, "")
		sharedPrefs.putString(Key.userPhoto, "")
		sharedPrefs.putString(Key.userName, "")
		sharedPrefs.putString(Key.userEmail, "")
	}
}