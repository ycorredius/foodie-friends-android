package com.example.foodiefriends.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.foodiefriends.Key
import com.example.foodiefriends.data.repo.UserRepository
import com.example.foodiefriends.int
import com.example.foodiefriends.network.AuthService
import com.example.foodiefriends.putInt
import com.example.foodiefriends.putString
import com.example.foodiefriends.sharedPrefs
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
	private val authService: AuthService,
	@ApplicationContext context: Context,
	private val sharedPrefs: SharedPreferences = sharedPrefs(context),
	private val userRepository: UserRepository
) {
	// TODO: Figure out how throw a proper error
	suspend fun loginUser(email: String, password: String): Response<UserResponse>? {
		val response = authService.loginUser(Auth(email, password))
		return try {
			if (response.isSuccessful) {
				response.body()?.let {
					sharedPrefs.putString(Key.accessToken, it.token)
					sharedPrefs.putInt(Key.userId, it.user.data.id)
				}
			}
			response
		} catch (e: HttpException) {
			Log.e("AuthRepoLoginError", "Something went wrong trying to login", e)
			//TODO: Return an actual error response and not null
			null
		}
	}

	// TODO: Figure out how throw a proper error
	suspend fun signUpUser(email: String, password: String): Response<UserResponse>? {
		return try {
			val response = authService.signUpUser(Auth(email, password))
			if (response.isSuccessful) {
				response.body()?.let { resp ->
					resp.token.let { sharedPrefs.putString(Key.accessToken, it) }
					resp.user.data.id.let { sharedPrefs.putInt(Key.userId, it) }
					resp.toLocalUser().let { user -> userRepository.insertUser(user) }
				}
			}
			response
		} catch (e: HttpException) {
			Log.e("AuthRepoLoginError", "Something went wrong trying to login", e)
			null
		}
	}

	//TODO: I need to change where this is located. Possibly create a repo that specifically deals with status items.
	//This repo should only handle authenticatin transactions i.e. login, signup, log
	fun getLocalUser(): Flow<LocalUser?> {
		val id = sharedPrefs.int(Key.userId)
		return userRepository.getUser(id)
	}

	fun logout() {
		sharedPrefs.putString(Key.accessToken, "")
	}
}