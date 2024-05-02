package com.example.foodiefriends.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class LocalRecipe(
	@PrimaryKey
	val id: Int = 0,
	val name: String = "",
	val cookTime: Int = 0,
	val difficulty: String = "",
	val instructions: String = "",
	val isPrivate: Boolean = false,
	val mealType: String = "",
	val prepTime: Int = 0,
	val yield: Int = 0,
	val thumbnailUrl: String? = "",
	val jumboUrl: String? = "",

	)