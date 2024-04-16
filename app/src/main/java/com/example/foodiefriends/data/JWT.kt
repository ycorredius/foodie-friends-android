package com.example.foodiefriends.data

import kotlinx.serialization.Serializable

@Serializable
data class JWT(
	val token: String?,
	val error: List<String>?
)