package com.example.foodiefriends.data

data class Ingredient(
	val attributes: IngredientAttributes = IngredientAttributes()
)

data class IngredientAttributes(
	val name: String = ""
)
