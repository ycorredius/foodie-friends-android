package com.example.foodiefriends.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foodiefriends.AppState
import com.example.foodiefriends.R
import com.example.foodiefriends.ScreenRoutes
import com.example.foodiefriends.ui.NavigationDestination

object SettingsDestination: NavigationDestination{
	override val route = "setting"
	override val titleRes = R.string.setting
}

@Composable
fun SettingsScreen(
	viewModel: SettingViewModel = hiltViewModel(),
	appState: AppState
){
	Column {
		TextButton(onClick = {
			appState.navController.currentDestination?.route?.let { appState.openAndPopUp(ScreenRoutes.Auth.Login.route,it) }
			viewModel.logout() }) {
			Text(text = "Logout")
		}
	}
}

