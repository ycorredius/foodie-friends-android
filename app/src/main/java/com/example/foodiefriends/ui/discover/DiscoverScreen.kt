package com.example.foodiefriends.ui.discover

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foodiefriends.AppState
import com.example.foodiefriends.R
import com.example.foodiefriends.TopBarRow
import com.example.foodiefriends.data.Recipe
import com.example.foodiefriends.ui.NavigationDestination
import com.example.foodiefriends.ui.reusables.ErrorScreen
import com.example.foodiefriends.ui.reusables.LoadingScreen
import com.example.foodiefriends.ui.reusables.RecipeCard
import kotlinx.coroutines.launch

object DiscoverDestination : NavigationDestination {
	override val titleRes = R.string.discover
	override val route = "discover"
}

// TODO: update this screen to show no result if search result come back null
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DiscoverScreen(
	viewModel: DiscoverViewModel = hiltViewModel(),
	appState: AppState
) {
	val uiState = viewModel.uiState.collectAsState()
	val coroutine = rememberCoroutineScope()
	Scaffold(
		topBar = {
			if (appState.shouldShowTopBar) TopBarRow(appState = appState, discoverGetRecipes = {
				coroutine.launch {
					viewModel.getRecipes(it)
				}
			})
		},
	) { padding ->
		Column(
			modifier = Modifier.padding(padding),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			when (val recipeUiState = uiState.value) {
				is DiscoverRecipeUiState.Success -> RecipeList(
					recipes = recipeUiState.recipes.recipes,
					appState = appState,
				)

				is DiscoverRecipeUiState.Loading -> LoadingScreen()
				is DiscoverRecipeUiState.Error -> ErrorScreen(error = recipeUiState.error)
			}
		}
	}
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecipeList(
	recipes: List<Recipe>,
	appState: AppState,
) {
	LazyColumn(
		contentPadding = PaddingValues(20.dp, 0.dp),
		verticalArrangement = Arrangement.SpaceAround
	) {
		items(recipes) { recipe ->
			RecipeCard(recipe = recipe, appState = appState)
		}
	}
}