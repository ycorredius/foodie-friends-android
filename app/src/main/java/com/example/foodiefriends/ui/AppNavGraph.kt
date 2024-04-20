package com.example.foodiefriends.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.foodiefriends.AppState
import com.example.foodiefriends.ScreenRoutes
import com.example.foodiefriends.ui.dashboard.DashboardScreen
import com.example.foodiefriends.ui.discover.DiscoverScreen
import com.example.foodiefriends.ui.login.LoginScreen
import com.example.foodiefriends.ui.login.SignupScreen
import com.example.foodiefriends.ui.main.MainScreen
import com.example.foodiefriends.ui.recipe.RecipeDestination
import com.example.foodiefriends.ui.recipe.RecipeScreen
import com.example.foodiefriends.ui.settings.SettingsScreen
import com.example.foodiefriends.ui.user.UserScreen

@Composable
fun AppNavHost(
	appState: AppState
) {
	Scaffold { padding ->
		NavHost(
			navController = appState.navController,
			startDestination = ScreenRoutes.Main.route,
			modifier = Modifier.padding(padding)
		) {
			composable(ScreenRoutes.Main.route) {
				MainScreen(openAndPopUp = { route, popUp ->
					appState.openAndPopUp(route, popUp)
				})
			}

			composable(
				// "recipe/{recipeId}"
				route = RecipeDestination.routeWithArgs,
				arguments = listOf(
					// "recipeId"
					navArgument(RecipeDestination.recipeIdArgs) {
						// recipeId is type Int
						type = NavType.IntType
					})
			) {
				RecipeScreen()
			}

			composable(ScreenRoutes.Dashboard.route) {
				DashboardScreen(appState = appState)
			}

			composable(ScreenRoutes.Auth.Login.route) {
				LoginScreen(openAndPopUp = { route, popUp ->
					appState.openAndPopUp(route, popUp)
				})
			}

			composable(ScreenRoutes.Auth.Signup.route) {
				SignupScreen(openAndPopUp = { route, popUp ->
					appState.openAndPopUp(route, popUp)
				})
			}

			composable(ScreenRoutes.Dashboard.route) {
				DashboardScreen(appState = appState)
			}

			composable(ScreenRoutes.User.route) {
				UserScreen()
			}

			composable(ScreenRoutes.Settings.route) {
				SettingsScreen(appState = appState)
			}

			composable(ScreenRoutes.Discover.route) {
				DiscoverScreen(appState = appState)
			}
		}
	}
}