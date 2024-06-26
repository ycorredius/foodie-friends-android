package com.example.foodiefriends.ui.main

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foodiefriends.R
import com.example.foodiefriends.ui.NavigationDestination
import kotlinx.coroutines.delay

object MainDestination : NavigationDestination {
	override val route = "Main"
	override val titleRes = R.string.main
}

@Composable
fun MainScreen(
	mainViewModel: MainViewModel = hiltViewModel(),
	openAndPopUp: (String, String) -> Unit,
) {
	Text(text = "This is the main view!")
	LaunchedEffect(true) {
		delay(3_000L)
		mainViewModel.onAppStart(openAndPopUp)
	}
}