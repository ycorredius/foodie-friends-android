package com.example.foodiefriends.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class LocalUser(
	@PrimaryKey
	val id: Int = 0,
	val token: String? = "",
	val name: String = "",
	val email: String = "",
	val avatar: String? = ""
)