package com.example.foodiefriends.ui.recipe

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.foodiefriends.R
import com.example.foodiefriends.data.Recipe
import com.example.foodiefriends.ui.NavigationDestination
import com.example.foodiefriends.ui.reusables.LoadingScreen

object RecipeDestination : NavigationDestination {
	override val route = "recipe"
	override val titleRes = R.string.recipe
	const val recipeIdArgs = "recipeId"
	val routeWithArgs = "$route/{$recipeIdArgs}"
}


@Composable
fun RecipeScreen(
	viewModel: RecipeViewModel = hiltViewModel()
) {
	val recipeUiState = viewModel.uiState.collectAsState()
	when (val uiState = recipeUiState.value) {
		is RecipeDetailUiState.Success -> RecipeBody(recipe = uiState.recipe.recipe)
		is RecipeDetailUiState.Loading -> LoadingScreen()
		is RecipeDetailUiState.Error -> Text(text = "Shit broke")
	}
}

@Composable
fun RecipeBody(
	recipe: Recipe
) {
	Column {
		Text(text = recipe.attributes.name)
		AsyncImage(
			model = ImageRequest.Builder(LocalContext.current)
				.data(recipe.attributes.jumboUrl)
				.build(),
			contentDescription = "Recipe image",
			contentScale = ContentScale.Crop
		)
	}
}