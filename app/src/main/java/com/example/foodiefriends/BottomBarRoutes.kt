package com.example.foodiefriends

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.foodiefriends.ui.dashboard.DashboardDestination
import com.example.foodiefriends.ui.discover.DiscoverDestination
import com.example.foodiefriends.ui.login.LoginDestination
import com.example.foodiefriends.ui.login.SignupDestination
import com.example.foodiefriends.ui.main.MainDestination
import com.example.foodiefriends.ui.recipe.RecipeDestination
import com.example.foodiefriends.ui.settings.SettingsDestination
import com.example.foodiefriends.ui.user.UserDestination

sealed class ScreenRoutes(val route: String) {
	data object Main : ScreenRoutes(MainDestination.route)
	data object Dashboard : ScreenRoutes(DashboardDestination.route)
	data object Discover : ScreenRoutes(DiscoverDestination.route)
	data object User : ScreenRoutes(UserDestination.route)
	data object Settings : ScreenRoutes(SettingsDestination.route)
	data object Recipe : ScreenRoutes(RecipeDestination.routeWithArgs)
	data object Auth : ScreenRoutes("auth") {
		data object Login : ScreenRoutes(LoginDestination.route)
		data object Signup : ScreenRoutes(SignupDestination.route)
	}
}

enum class BottomBarRoutes(
	val id: Int,
	@StringRes val title: Int,
	val route: String,
	@DrawableRes val icon: Int
) {
	DASHBOARD(id = 1, R.string.my_recipes, ScreenRoutes.Dashboard.route, R.drawable.book),
	DISCOVER(id = 2, R.string.discover, ScreenRoutes.Discover.route, R.drawable.find_icon),
	USERPROFILE(id = 3, R.string.user_profile, ScreenRoutes.User.route, R.drawable.profile_user)
}

enum class SearchBarRoutes(
	val route: String
) {
	DASHBOARD(ScreenRoutes.Dashboard.route),
	DISCOVER(ScreenRoutes.Discover.route)
}

enum class AuthRoutes(
	val route: String
) {
	LOGIN(LoginDestination.route),
	SIGNUP(SignupDestination.route),
	MAIN(MainDestination.route)
}