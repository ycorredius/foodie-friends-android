package com.example.foodiefriends.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.foodiefriends.Key
import com.example.foodiefriends.SERVER_ERROR
import com.example.foodiefriends.network.RecipeService
import com.example.foodiefriends.printMsg
import com.example.foodiefriends.sharedPrefs
import com.example.foodiefriends.string
import com.example.foodiefriends.ui.Errors
import com.example.foodiefriends.ui.dashboard.RecipesUiState
import com.example.foodiefriends.ui.recipe.RecipeUiState
import com.example.foodiefriends.ui.recipe.UserRecipe
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow

class RecipeRepository(
	private val recipeService: RecipeService,
	@ApplicationContext context: Context,
	private val sharedPrefs: SharedPreferences = sharedPrefs(context)
) {
	private var recipeData: MutableStateFlow<Recipe> = MutableStateFlow(Recipe())

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
			printMsg("@@@ Result Body: ${results.body()?.data}")
			recipeUiState
		} catch (e: Exception) {
			recipeUiState.error = handleError(e)
			recipeUiState
		}
	}
	//TODO: I'm not sure what I amd doing here but I know it wrong.
	suspend fun getRecipeDetails(id: Int): RecipeUiState {
		val recipeUiState = RecipeUiState(user = UserRecipe(userName = sharedPrefs.string(Key.userName) ?: "",
			userPhoto = sharedPrefs.string(Key.userPhoto) ?: ""))
		return try {
			val result = recipeService.getRecipeDetails(id)
			if (result.isSuccessful) {
				printMsg("@@@ Recipe wasn't found.")
				result.body()?.let {
					recipeUiState.recipe = it.data
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