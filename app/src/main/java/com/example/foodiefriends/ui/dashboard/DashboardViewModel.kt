package com.example.foodiefriends.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiefriends.data.Recipe
import com.example.foodiefriends.data.RecipeRepository
import com.example.foodiefriends.printMsg
import com.example.foodiefriends.ui.Errors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
	private val recipeRepository: RecipeRepository
) : ViewModel() {
	private val _uiState: MutableStateFlow<DashboardUiState> =
		MutableStateFlow(DashboardUiState.Loading)
	val uiState: StateFlow<DashboardUiState> = _uiState

	init {
		viewModelScope.launch {
			getRecipes()
		}
	}
	suspend fun getRecipes(name: String = "") {
		_uiState.value = DashboardUiState.Loading
		val result = recipeRepository.getUserRecipes(name)

		_uiState.value = if (result.error == Errors.None) {
			printMsg("@@@@ Server offline error: $result")
			DashboardUiState.Success(result)
		} else {
			DashboardUiState.Error(result.error)
		}
	}
}

// TODO: Update this sealed interface and the discover view model ui state to be the same. There is no difference between the two.
sealed interface DashboardUiState {
	data class Success(val recipes: RecipesUiState) : DashboardUiState
	data object Loading : DashboardUiState
	//Set error as string for now. Will revisit what is appropriate error
	data class Error(val error: Errors) : DashboardUiState
}

data class RecipesUiState(
	var recipes: List<Recipe> = emptyList(),
	var error: Errors = Errors.None
)