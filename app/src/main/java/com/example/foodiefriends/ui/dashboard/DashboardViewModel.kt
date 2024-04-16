package com.example.foodiefriends.ui.dashboard

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiefriends.data.Recipe
import com.example.foodiefriends.data.RecipeRepository
import com.example.foodiefriends.network.RecipeService
import com.example.foodiefriends.printMsg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
	private val recipeRepository: RecipeRepository
): ViewModel() {
	// TODO: Create a non-mutable list for recipes retrieved from api.
	// TODO: Create a mutable list that can take recipes from above list to display on screen.
	// TODO: Create a function that take the non-mutable list and filters based on name of recipes to return the mutable list that will be displayed on screen.
	// TODO: Figure out how update this list without triggering screen recompose.
	private val _uiState: MutableStateFlow<DashboardUiState> =  MutableStateFlow(DashboardUiState.Loading)
	val uiState: StateFlow<DashboardUiState> = _uiState

	init {
		viewModelScope.launch {
			getRecipes()
		}
	}
	suspend fun getRecipes(name: String = ""){
		delay(2_000L)
		_uiState.value = DashboardUiState.Loading
		_uiState.value = try {
			printMsg("@@@@ Things going well?")
			val result = recipeRepository.getUserRecipes(name)
			DashboardUiState.Success(result)
		} catch (e: Exception){
			printMsg("@@@@ An Error occurred fetching recipes: $e")
			DashboardUiState.Error(e.toString())
		}
	}
}

sealed interface DashboardUiState{
	data class Success(val recipes: RecipesUiState): DashboardUiState
	data object Loading: DashboardUiState
	//Set error as string for now. Will revisit what is appropriate error
	data class Error(val errors: String): DashboardUiState
}

data class RecipesUiState(
	var recipes: List<Recipe> = emptyList(),
	val errors: List<String> = emptyList(),
)