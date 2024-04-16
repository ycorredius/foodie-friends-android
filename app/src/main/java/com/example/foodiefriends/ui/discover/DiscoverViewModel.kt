package com.example.foodiefriends.ui.discover

import android.telephony.TelephonyScanManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiefriends.data.Recipe
import com.example.foodiefriends.data.RecipeDataSource
import com.example.foodiefriends.data.RecipeRepository
import com.example.foodiefriends.network.RecipeService
import com.example.foodiefriends.printMsg
import com.example.foodiefriends.ui.dashboard.RecipesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
	private val recipeRepository: RecipeRepository,
	private val recipeDataSource: RecipeDataSource
): ViewModel(){

	private val _uiState: MutableStateFlow<DiscoverRecipeUiState> = MutableStateFlow<DiscoverRecipeUiState>(DiscoverRecipeUiState.Loading)
	val uiState: StateFlow<DiscoverRecipeUiState> = _uiState

	init {
		viewModelScope.launch {
			getRecipes()
		}
	}

	 suspend fun getRecipes(name: String = "") {
		_uiState.value = DiscoverRecipeUiState.Loading
		_uiState.value = try {
			val result = recipeRepository.getRecipes(name)
			DiscoverRecipeUiState.Success(recipes = result)
		} catch (e: Exception){
			printMsg("@@@ Something went wrong $e")
			DiscoverRecipeUiState.Error
		}
	}

	fun setRecipeData(recipe: Recipe){
		recipeDataSource.updateRecipe(recipe)
	}
}

sealed interface DiscoverRecipeUiState{
	data class Success(val recipes: RecipesUiState): DiscoverRecipeUiState
	data object Error: DiscoverRecipeUiState
	data object Loading: DiscoverRecipeUiState
}

