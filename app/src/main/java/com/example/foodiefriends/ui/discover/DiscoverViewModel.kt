package com.example.foodiefriends.ui.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiefriends.data.Recipe
import com.example.foodiefriends.data.RecipeRepository
import com.example.foodiefriends.printMsg
import com.example.foodiefriends.ui.Errors
import com.example.foodiefriends.ui.dashboard.RecipesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
	private val recipeRepository: RecipeRepository,
) : ViewModel() {

	private val _uiState: MutableStateFlow<DiscoverRecipeUiState> =
		MutableStateFlow<DiscoverRecipeUiState>(DiscoverRecipeUiState.Loading)
	val uiState: StateFlow<DiscoverRecipeUiState> = _uiState

	init {
		viewModelScope.launch {
			getRecipes()
		}
	}

	suspend fun getRecipes(name: String = "") {
		delay(3_000L)
		_uiState.value = DiscoverRecipeUiState.Loading
		val result = recipeRepository.getRecipes(name)

		_uiState.value = if (result.recipes.isNotEmpty()) {
			printMsg("@@@@ Server offline error: $result")
			DiscoverRecipeUiState.Success(result)
		} else {
			DiscoverRecipeUiState.Error(result.error)
		}
	}

	fun setRecipeData(recipe: Recipe) {
		recipeRepository.updateRecipe(recipe)
	}
}

sealed interface DiscoverRecipeUiState {
	data class Success(val recipes: RecipesUiState) : DiscoverRecipeUiState
	data class Error(val error: Errors) : DiscoverRecipeUiState
	data object Loading : DiscoverRecipeUiState
}

