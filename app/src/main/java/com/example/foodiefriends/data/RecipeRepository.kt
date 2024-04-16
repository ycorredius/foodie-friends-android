package com.example.foodiefriends.data

import android.util.Log
import com.example.foodiefriends.network.RecipeService
import com.example.foodiefriends.printMsg
import com.example.foodiefriends.ui.dashboard.RecipesUiState

class RecipeRepository(
	private val recipeService: RecipeService
) {
	private var recipeData: Recipe? = null
	private var cacheRecipes: List<Recipe>? = null

	fun getRecipe(): Recipe?{
		return recipeData
	}

	fun updateRecipe(recipe: Recipe){
		recipeData = recipe
	}

	suspend fun getUserRecipes(name: String = ""): RecipesUiState {
		val recipeUiState = RecipesUiState()
		return try {
			val results = recipeService.getUserRecipes(name)
			if (results.isSuccessful) {
				results.body()?.let { recipeResponse ->
					recipeResponse.data?.let {
						recipeUiState.recipes = it }
				}
			}
			recipeUiState
		} catch (e: Exception) {
			Log.e("RecipeRepositoryError", "$e")
			recipeUiState
		}
	}

	suspend fun getRecipes(name: String =""): RecipesUiState{
		val recipeUiState = RecipesUiState()
		return try {
			val results = recipeService.getRecipes(name)
			if (results.isSuccessful) {
				results.body()?.let { recipeResponse ->
					recipeResponse.data?.let { recipeUiState.recipes = it }
				}
			}
			printMsg("@@@ Result Body: ${results.body()?.data}")
			recipeUiState
		} catch (e: Exception) {
			Log.e("RecipeRepositoryError", "$e")
			recipeUiState
		}
	}
}