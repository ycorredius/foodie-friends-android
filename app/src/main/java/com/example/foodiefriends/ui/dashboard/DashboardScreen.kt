package com.example.foodiefriends.ui.dashboard

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
import com.example.foodiefriends.data.Recipe
import com.example.foodiefriends.ui.NavigationDestination
import com.example.foodiefriends.ui.reusables.LoadingScreen
import com.example.foodiefriends.ui.reusables.RecipeCard
import com.example.foodiefriends.ui.reusables.RecipeSearchBar
import kotlinx.coroutines.launch

object DashboardDestination : NavigationDestination {
	override val titleRes = R.string.dashboard
	override val route = "Dashboard"
}

@Composable
fun DashboardScreen(
	dashboardViewModel: DashboardViewModel = hiltViewModel()
) {
	val dashboardUiState = dashboardViewModel.uiState.collectAsState()
	val coroutine = rememberCoroutineScope()
	Column(
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		RecipeSearchBar(getRecipes = {
			coroutine.launch {
				dashboardViewModel.getRecipes(it)
			}
		})
		when (val uiState = dashboardUiState.value) {
			//TODO: update uiState.recipes.recipes to be less confusing.
			is DashboardUiState.Success -> DashboardBody(
				recipes = uiState.recipes.recipes
			)

			is DashboardUiState.Loading -> LoadingScreen()
			//TODO: create a way to retry on fetching recipe data.
			is DashboardUiState.Error -> Text(text = uiState.errors)
		}
	}
}

@Composable
fun DashboardBody(
	recipes: List<Recipe>,
) {
	LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 150.dp)) {
		items(recipes) { recipe ->
			RecipeCard(recipe)
		}
	}
}