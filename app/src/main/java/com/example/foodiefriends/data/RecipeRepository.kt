package com.example.foodiefriends.data

import android.util.Log
import com.example.foodiefriends.SERVER_ERROR
import com.example.foodiefriends.network.RecipeService
import com.example.foodiefriends.printMsg
import com.example.foodiefriends.ui.Errors
import com.example.foodiefriends.ui.dashboard.RecipesUiState
import com.example.foodiefriends.ui.recipe.RecipeUiState

class RecipeRepository(
	private val recipeService: RecipeService,
) {
	// TODO: Figure out best way to cache recipes in order not to call too many times.
	// TODO: Implement a service that saves recipes to server and local db.
	suspend fun getUserRecipes(name: String = ""): RecipesUiState {
		val recipeUiState = RecipesUiState()
		return try {
			val results = recipeService.getUserRecipes(name)
			if (results.isSuccessful) {
				results.body()?.let { recipeResponse ->
					recipeResponse.data?.let {
						recipeUiState.recipes = it
					}
				}
			}
			recipeUiState
		} catch (e: Exception) {
			recipeUiState.error = handleError(e)
			recipeUiState
		}
	}

	suspend fun getRecipes(name: String = ""): RecipesUiState {
		val recipeUiState = RecipesUiState()
		return try {
			val results = recipeService.getRecipes(name)
			if (results.isSuccessful) {
				results.body()?.let { recipeResponse ->
					recipeResponse.data?.let { recipeUiState.recipes = it }
				}
			}
			recipeUiState
		} catch (e: Exception) {
			recipeUiState.error = handleError(e)
			recipeUiState
		}
	}

	//TODO: I'm not sure what I amd doing here but I know it wrong.
	suspend fun getRecipeDetails(id: Int): RecipeUiState {
		val recipeUiState = RecipeUiState()
		return try {
			val result = recipeService.getRecipeDetails(id)
			if (result.isSuccessful) {
				printMsg("@@@ Recipe wasn't found.")
				result.body()?.let {
					recipeUiState.recipe = it.data
					recipeUiState.ingredients = it.included
					recipeUiState.user = it.data.attributes.user
				}
			}
			recipeUiState
		} catch (e: Exception) {
			printMsg("@@@ Recipe wasn't found.")
			recipeUiState.error = e.toString()
			recipeUiState
		}
	}

	private fun handleError(e: Exception): Errors {
		Log.e("RecipeRepositoryError", "${e.message}")
		return when {
			e.message?.contains(SERVER_ERROR) == true -> Errors.ServerError
			else -> Errors.Other
		}
	}
}