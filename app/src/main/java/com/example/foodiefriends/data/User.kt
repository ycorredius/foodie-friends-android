package com.example.foodiefriends.data

import com.google.gson.annotations.SerializedName


data class UserResponse(
	val token: String = "",
	val user: User,
	val errors: List<String> = emptyList()
)

data class User(
	val data: UserData = UserData()
)

data class UserData(
	val id: Int = 0,
	val attributes: UserAttributes = UserAttributes()
)

data class UserAttributes(
	val token: String = "",
	@SerializedName("full_name")
	val name: String = "",
	val email: String = "",
	@SerializedName("avatar_url")
	val avatar: String? = ""
)

data class RecipeUser(
	@SerializedName("first_name")
	val firstName: String = "",
	@SerializedName("last_name")
	val lastName: String = "",
	val email: String = "",
	@SerializedName("avatar_url")
	val avatar: String = ""
)