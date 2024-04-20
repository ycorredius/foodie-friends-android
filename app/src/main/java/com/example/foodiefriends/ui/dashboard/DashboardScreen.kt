package com.example.foodiefriends.ui.dashboard

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

object DashboardDestination : NavigationDestination {
	override val titleRes = R.string.dashboard
	override val route = "dashboard"
}

// TODO: update this screen to show no result if search result come back null
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DashboardScreen(
	dashboardViewModel: DashboardViewModel = hiltViewModel(),
	appState: AppState
) {
	val dashboardUiState = dashboardViewModel.uiState.collectAsState()
	val coroutine = rememberCoroutineScope()
	Scaffold(
		topBar = {
			if (appState.shouldShowTopBar) TopBarRow(appState = appState, dashboardGetRecipes = {
				coroutine.launch {
					dashboardViewModel.getRecipes(it)
				}
			})
		},
	) { padding ->
		Column(
			modifier = Modifier.padding(padding),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			when (val uiState = dashboardUiState.value) {
				//TODO: update uiState.recipes.recipes to be less confusing.
				is DashboardUiState.Success -> DashboardBody(
					recipes = uiState.recipes.recipes,
					appState
				)

				is DashboardUiState.Loading -> LoadingScreen()
				//TODO: create a way to retry on fetching recipe data.
				is DashboardUiState.Error -> ErrorScreen(error = uiState.error)
			}
		}
	}
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DashboardBody(
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