package com.example.foodiefriends.ui.user

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiefriends.data.AuthRepository
import com.example.foodiefriends.data.Recipe
import com.example.foodiefriends.data.RecipeRepository
import com.example.foodiefriends.ui.dashboard.RecipesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
	private val authRepository: AuthRepository,
	private val recipeRepository: RecipeRepository
) : ViewModel() {

	private val _uiState: MutableStateFlow<MeUiState> = MutableStateFlow(MeUiState.Loading)
	val uiState: StateFlow<MeUiState> = _uiState

	init {
		viewModelScope.launch {
			initUserProfileScreen()
		}
	}

	private suspend fun initUserProfileScreen(){
		_uiState.value = MeUiState.Loading
		try {
			_uiState.value = MeUiState.Success(
				me = authRepository.getMe(),
				recipes = recipeRepository.getUserRecipes()
			)
		} catch (e: Exception){
			_uiState.value = MeUiState.Error
		}
	}
}

sealed interface MeUiState {
	data class Success(val me: Me, val recipes: RecipesUiState) : MeUiState
	data object Loading : MeUiState
	data object Error : MeUiState
}

data class Me(
	val name: String = "",
	val avatarUrl: String = ""
)

data class ItemTab(
	val title: String,
)

