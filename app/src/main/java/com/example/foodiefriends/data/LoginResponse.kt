package com.example.foodiefriends.data

import com.google.gson.annotations.SerializedName

data class LoginResponse(
	@SerializedName("token")
	val token: String
)