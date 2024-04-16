package com.example.foodiefriends.ui.discover

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foodiefriends.R
import com.example.foodiefriends.ui.NavigationDestination
import com.example.foodiefriends.ui.dashboard.RecipesUiState
import com.example.foodiefriends.ui.reusables.LoadingScreen
import com.example.foodiefriends.ui.reusables.RecipeCard
import com.example.foodiefriends.ui.reusables.RecipeSearchBar
import kotlinx.coroutines.launch

object DiscoverDestination : NavigationDestination {
	override val titleRes = R.string.discover
	override val route = "discover"
}

@Composable
fun DiscoverScreen(
	viewModel: DiscoverViewModel = hiltViewModel()
) {
	val coroutine = rememberCoroutineScope()
	Column(
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		RecipeSearchBar(getRecipes = {
			coroutine.launch {
				viewModel.getRecipes(it)
			}
		})
		val uiState = viewModel.uiState.collectAsState()
		when (val recipeUiState = uiState.value) {
			is DiscoverRecipeUiState.Success -> RecipeList(recipes = recipeUiState.recipes)
			is DiscoverRecipeUiState.Loading -> LoadingScreen()
			is DiscoverRecipeUiState.Error -> Text(text = "Something went wrong")
		}
	}
}

@Composable
fun RecipeList(
	recipes: RecipesUiState
) {
	LazyVerticalGrid(columns = GridCells.Adaptive(150.dp)) {
		items(recipes.recipes) { recipe ->
			RecipeCard(recipe = recipe)
		}
	}
}