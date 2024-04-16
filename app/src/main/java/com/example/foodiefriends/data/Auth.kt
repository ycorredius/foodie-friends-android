package com.example.foodiefriends.data

import com.google.gson.annotations.SerializedName

data class Auth(
	@SerializedName("email")
	val email: String,
	val password: String,
)