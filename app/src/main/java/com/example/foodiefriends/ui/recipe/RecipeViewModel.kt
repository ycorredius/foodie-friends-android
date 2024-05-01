package com.example.foodiefriends.ui.recipe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiefriends.data.Recipe
import com.example.foodiefriends.data.RecipeRepository
import com.example.foodiefriends.printMsg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
	private val recipeRepository: RecipeRepository,
	savedStateHandle: SavedStateHandle
) : ViewModel() {

	private val recipeId: Int =
		checkNotNull(savedStateHandle[RecipeDestination.recipeIdArgs])

	private val _uiState: MutableStateFlow<RecipeDetailUiState> =
		MutableStateFlow(RecipeDetailUiState.Loading)
	val uiState: StateFlow<RecipeDetailUiState> = _uiState

	init {
		viewModelScope.launch {
			getRecipe(recipeId)
		}
	}

	private suspend fun getRecipe(id: Int) {
		_uiState.value = RecipeDetailUiState.Loading
		_uiState.value = try {
			val result = recipeRepository.getRecipeDetails(id)
			RecipeDetailUiState.Success(result)
		} catch (e: Exception) {
			printMsg("@@@ Something has gone wrong in recipeveiwmodel: $e")
			RecipeDetailUiState.Error
		}
	}
}

sealed interface RecipeDetailUiState {
	data class Success(val recipe: RecipeUiState) : RecipeDetailUiState
	data object Loading : RecipeDetailUiState
	data object Error : RecipeDetailUiState
}

data class RecipeUiState(
	var recipe: Recipe = Recipe(),
	var error: String = "",
	var user: UserRecipe
)

data class UserRecipe(
	val userName: String,
	val userPhoto: String,
)

