package com.example.foodiefriends.data

import com.google.gson.annotations.SerializedName


data class UserResponse(
	val token: String = "",
	val user: User,
	val errors: List<String> = emptyList()
)

data class User(
	val data: UserData
)

data class UserData(
	val id: Int = 0,
	val attributes: UserAttributes
)

data class UserAttributes(
	val token: String = "",
	@SerializedName("full_name")
	val name: String = "",
	val email: String = "",
	@SerializedName("avatar_url")
	val avatar: String = ""
)