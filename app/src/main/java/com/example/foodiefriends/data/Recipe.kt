package com.example.foodiefriends.data

import com.google.gson.annotations.SerializedName

data class RecipeResponse(
	val data: List<Recipe>? = emptyList()
)

//TODO: remap attributes
data class Recipe(
	val attributes: RecipeAttributes,
	val relationships: RecipeRelationships
)

data class RecipeRelationships(
	val ingredients: List<Ingredient> = emptyList()
)

data class RecipeAttributes(
	val id: Int = 0,
	val name: String = "",
	@SerializedName("cook_time")
	val cookTime: Int = 0,
	val difficulty: String = "",
	val instructions: String = "",
	@SerializedName("is_private")
	val isPrivate: Boolean = false,
	@SerializedName("meal_type")
	val mealType: String = "",
	@SerializedName("prep_time")
	val prepTime: Int = 0,
	val yield: Int = 0,
	@SerializedName("thumbnail_url")
	val thumbnailUrl: String? = "",
	@SerializedName("jumbo_url")
	val jumboUrl: String = ""
)
