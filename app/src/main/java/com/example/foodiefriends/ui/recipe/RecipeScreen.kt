package com.example.foodiefriends.ui.recipe

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foodiefriends.AppState
import com.example.foodiefriends.R
import com.example.foodiefriends.TopBarRow
import com.example.foodiefriends.data.Recipe
import com.example.foodiefriends.ui.NavigationDestination
import com.example.foodiefriends.ui.reusables.ImageComposable
import com.example.foodiefriends.ui.reusables.LoadingScreen

object RecipeDestination : NavigationDestination {
	override val route = "recipe"
	override val titleRes = R.string.recipe
	const val recipeIdArgs = "recipeId"
	val routeWithArgs = "$route/{$recipeIdArgs}"
}


@Composable
fun RecipeScreen(
	viewModel: RecipeViewModel = hiltViewModel(),
	appState: AppState
) {
	val recipeUiState = viewModel.uiState.collectAsState()
	Scaffold(
		topBar = {
			TopBarRow(appState = appState)
		}
	) { padding ->
		Box(modifier = Modifier.padding(padding)) {
			when (val uiState = recipeUiState.value) {
				is RecipeDetailUiState.Success -> RecipeBody(recipe = uiState.recipe)
				is RecipeDetailUiState.Loading -> LoadingScreen()
				is RecipeDetailUiState.Error -> Text(text = "Shit broke")
			}
		}
	}
}

@Composable
fun RecipeBody(
	recipe: RecipeUiState
) {
	Column(modifier = Modifier.fillMaxWidth()) {
		RecipeDataSource(user = recipe.user)
		RecipeData(recipe = recipe.recipe)
	}
}

@Composable
fun RecipeDataSource(
	user: UserRecipe
) {
	Row(
		verticalAlignment = Alignment.CenterVertically
	) {
		if (user.userPhoto.isNotEmpty()) {
			ImageComposable(url = user.userPhoto)
		} else {
			Image(
				modifier = Modifier
					.size(50.dp)
					.padding(10.dp, 0.dp),
				painter = painterResource(id = R.drawable.profile_user),
				contentDescription = "Default user profile",
			)
		}
		Text(text = user.userName, fontSize = 18.sp)
	}
}

@Composable
fun RecipeData(
	recipe: Recipe
) {
	Column {
		Text(
			modifier = Modifier.padding(10.dp, 10.dp),
			text = recipe.attributes.name,
			fontSize = 24.sp,
			fontWeight = FontWeight.SemiBold,
			textAlign = TextAlign.Start
		)
		if (recipe.attributes.jumboUrl?.isNotEmpty() == true) {
			ImageComposable(url = recipe.attributes.jumboUrl)
		} else {
			Image(
				modifier = Modifier.fillMaxWidth(),
				painter = painterResource(id = R.drawable.default_food),
				contentDescription = "Default food image",
				contentScale = ContentScale.FillWidth

			)
		}
	}
}