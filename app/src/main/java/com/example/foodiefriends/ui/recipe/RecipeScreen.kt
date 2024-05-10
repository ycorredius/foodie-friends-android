package com.example.foodiefriends.ui.recipe

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foodiefriends.AppState
import com.example.foodiefriends.R
import com.example.foodiefriends.TopBarRow
import com.example.foodiefriends.data.Ingredient
import com.example.foodiefriends.data.Recipe
import com.example.foodiefriends.data.RecipeUser
import com.example.foodiefriends.ui.NavigationDestination
import com.example.foodiefriends.ui.reusables.CoverImage
import com.example.foodiefriends.ui.reusables.ImageComposable
import com.example.foodiefriends.ui.reusables.LoadingScreen
import kotlinx.coroutines.launch

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
		Surface(modifier = Modifier.padding(padding)) {}
		when (val uiState = recipeUiState.value) {
			is RecipeDetailUiState.Success -> RecipeBody(recipe = uiState.recipe)
			is RecipeDetailUiState.Loading -> LoadingScreen()
			is RecipeDetailUiState.Error -> Text(text = "Shit broke")
		}
	}
}

@Composable
fun RecipeBody(
	recipe: RecipeUiState
) {
	Column(
		modifier = Modifier
			.background(
				color = MaterialTheme.colorScheme.primaryContainer,
			)
			.fillMaxHeight()
	) {
		CoverImage(url = recipe.recipe.attributes.jumboUrl)
		RecipeDataSource(user = recipe.user, avatar = recipe.recipe.attributes.userAvatar)
		RecipeData(recipe = recipe.recipe, ingredients = recipe.ingredients)
	}
}

@Composable
fun RecipeDataSource(
	user: RecipeUser,
	avatar: String?
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(20.dp, 0.dp),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.Center
	) {
		ImageComposable(url = avatar, userPhoto = true)
		Text(
			modifier = Modifier.padding(5.dp, 0.dp),
			text = "${user.firstName} ${user.lastName}",
			fontSize = 18.sp,
			fontWeight = FontWeight.SemiBold
		)
	}
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecipeData(
	recipe: Recipe,
	ingredients: List<Ingredient>
) {
	val coroutine = rememberCoroutineScope()
	val pagerState = rememberPagerState(pageCount = { RecipeTabs.entries.size })
	val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
	TabRow(
		modifier = Modifier.fillMaxWidth(),
		selectedTabIndex = pagerState.currentPage
	) {
		RecipeTabs.entries.forEachIndexed { index, recipeTabs ->
			Tab(selected = selectedTabIndex.value == index,
				onClick = {
					coroutine.launch {
						pagerState.animateScrollToPage(index)
					}
				},
				text = {
					Text(text = recipeTabs.title)
				})
		}
	}
	Box(
		modifier = Modifier
			.fillMaxSize()
	) {
		HorizontalPager(
			state = pagerState,

			) { index ->
			when (index) {
				0 -> IngredientsTab(ingredients = ingredients, recipe = recipe)
				else -> Text(text = RecipeTabs.entries[index].title)
			}
		}
	}
}

// Currently I hate this implemntation. I would like to have a BottomSheetScaffoldBar. There Are some issues getting that function so I will settle with this for now.
// Once I have the other screen implemented I will revisit how this looks
// TODO: Revisit using a BottomSheetScaffold rather than what is currenly implemented.
@Composable
fun IngredientsTab(
	recipe: Recipe,
	ingredients: List<Ingredient>
) {
	var showText by remember {
		mutableStateOf(false)
	}
	val maxLines: Int = if (showText) 20 else 2
	val textOverFlow: TextOverflow = if (showText) TextOverflow.Visible else TextOverflow.Ellipsis
	Column(
		modifier = Modifier.padding(20.dp, 10.dp)
	) {
		Box(Modifier.fillMaxWidth()) {
			Text(
				text = "Some kind of text goes here.Some kind of text goes here.Some kind of text goes here.Some kind of text goes hereSome kind of text goes hereSome kind of text goes hereSome kind of text goes here.Some kind of text goes here.Some kind of text goes here. ",
				fontWeight = FontWeight.Normal,
				maxLines = maxLines,
				fontSize = 14.sp,
				overflow = textOverFlow
			)
		}
		Row(
			modifier = Modifier.fillMaxWidth(),
			verticalAlignment = Alignment.Bottom,
			horizontalArrangement = Arrangement.End
		) {
			if (showText) {
				Text(
					text = "Show Less",
					fontSize = 12.sp,
					fontWeight = FontWeight.Light,
					color = MaterialTheme.colorScheme.primary,
					modifier = Modifier.clickable {
						showText = !showText
					})
			} else {
				Text(text = "Show More",
					fontSize = 12.sp,
					fontWeight = FontWeight.Light,
					color = MaterialTheme.colorScheme.primary,
					modifier = Modifier.clickable {
						showText = !showText
					})
			}
		}
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(10.dp),
			horizontalArrangement = Arrangement.Center
		) {
			Row {
				Icon(imageVector = Icons.Default.Schedule, contentDescription = "Timer icon")
				Text(text = "Cook Time: ${recipe.attributes.cookTime}", fontSize = 14.sp)
			}
			Spacer(modifier = Modifier.width(10.dp))
			Row {
				Text(text = "Prep Time: ${recipe.attributes.prepTime}", fontSize = 14.sp)
			}
		}
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.Center
		) {
			Text(text = "Difficulty: ${recipe.attributes.difficulty}", fontSize = 14.sp)
		}
		LazyColumn(
			contentPadding = PaddingValues(10.dp)
		) {
			items(ingredients) {
				Text(text = it.attributes.name)
			}
		}
	}
}

enum class RecipeTabs(
	val title: String
) {
	Ingredients("Ingredients"),
	Instructions("Instructions"),
	Nutrition("Nutrition Facts"),
}

