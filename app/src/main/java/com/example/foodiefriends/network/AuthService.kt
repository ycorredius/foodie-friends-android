package com.example.foodiefriends.network

import com.example.foodiefriends.data.Auth
import com.example.foodiefriends.data.UserResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
	@POST("auths")
	suspend fun loginUser(@Body body: Auth): Response<UserResponse>

	@POST("registration")
	suspend fun signUpUser(@Body body: Auth): Response<UserResponse>

	companion object {
		fun create(retrofit: Retrofit): AuthService = retrofit.create(AuthService::class.java)
	}
}