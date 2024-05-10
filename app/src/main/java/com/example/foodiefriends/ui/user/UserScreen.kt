package com.example.foodiefriends.ui.user

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foodiefriends.AppState
import com.example.foodiefriends.R
import com.example.foodiefriends.TopBarRow
import com.example.foodiefriends.data.LocalUser
import com.example.foodiefriends.data.Recipe
import com.example.foodiefriends.ui.NavigationDestination
import com.example.foodiefriends.ui.reusables.LoadingScreen
import com.example.foodiefriends.ui.reusables.ProfilePhoto
import com.example.foodiefriends.ui.reusables.RecipeCard
import kotlinx.coroutines.launch

object UserDestination : NavigationDestination {
	override val route = "userProfile"
	override val titleRes = R.string.user_profile
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UserProfileScreen(
	viewModel: UserViewModel = hiltViewModel(),
	appState: AppState
) {
	Scaffold(
		topBar = {
			TopBarRow(appState = appState)
		}
	) { padding ->
		Column(modifier = Modifier.padding(padding)) {
			val uiState = viewModel.uiState.collectAsState()
			when (val user = uiState.value) {
				MeUiState.Error -> Text(text = "Shit just broke!")
				MeUiState.Loading -> LoadingScreen()
				is MeUiState.Success -> user.localUser?.let {
					UserProfileBody(
						user = it,
						recipes = user.recipes.recipes,
						appState
					)
				}
			}
		}
	}
}

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UserProfileBody(
	user: LocalUser,
	recipes: List<Recipe>,
	appState: AppState
) {
	val coroutine = rememberCoroutineScope()
	val pagerState = rememberPagerState(pageCount = { HomeTabs.entries.size })
	val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
	Row(
		modifier = Modifier.padding(20.dp),
		horizontalArrangement = Arrangement.spacedBy(10.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		ProfilePhoto(url = user.avatar)
		Text(
			text = user.name,
			fontSize = 20.sp,
			fontWeight = FontWeight.Medium
		)
	}
	Row(
		modifier = Modifier.fillMaxWidth(),
		horizontalArrangement = Arrangement.Center
	) {
		OutlinedButton(onClick = { /*TODO*/ }) {
			Text(text = "Edit profile")
		}
	}

	TabRow(
		modifier = Modifier
			.padding(0.dp, 10.dp),
		selectedTabIndex = selectedTabIndex.value
	) {
		HomeTabs.entries.forEachIndexed { index, itemTab ->
			Tab(
				selected = selectedTabIndex.value == index, onClick = {
					//TODO: clean up this animation scroll
					//When scrolling a duplicate recipe image comes from the corner
					coroutine.launch {
						pagerState.animateScrollToPage(index)
					}
				}, text = {
					Text(
						text = itemTab.title
					)
				}
			)
		}
	}
	HorizontalPager(
		state = pagerState,
	) {
		Column(
			horizontalAlignment = Alignment.CenterHorizontally,
			modifier = Modifier.fillMaxWidth()
		) {
			if (selectedTabIndex.value == 0) {
				UserRecipes(
					recipes = recipes,
					appState = appState
				)
			} else {
				Text(text = HomeTabs.entries[selectedTabIndex.value].title)
			}
		}
	}
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UserRecipes(
	recipes: List<Recipe>,
	appState: AppState
) {
	LazyColumn() {
		items(recipes) {
			//TODO: This works great but I don't like the way it looks on screen.
			RecipeCard(recipe = it, appState)
		}
	}
}

enum class HomeTabs(
	val title: String
) {
	Created("Create"),
	Activity("Activity")
}