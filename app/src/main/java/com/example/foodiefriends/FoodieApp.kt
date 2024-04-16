package com.example.foodiefriends


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberAppState(navController: NavHostController = rememberNavController()) =
	remember(navController) {
		AppState(navController)
	}

@Stable
class AppState(
	val navController: NavHostController
) {
	fun openAndPopUp(route: String, popUp: String) {
		navController.navigate(route) {
			launchSingleTop = true
			popUpTo(popUp) { inclusive = true }
		}
	}

	private val routes = BottomBarRoutes.entries.map { it.route }
	private val authRoute = AuthRoutes.entries.map { it.route }

	val shouldBottomBarShow: Boolean
		@Composable get() =
			navController.currentBackStackEntryAsState().value?.destination?.route in routes

	val shouldShowTopBar: Boolean
		@Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route !in authRoute

	val shouldShowBackButton: Boolean
		@Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route == ScreenRoutes.Settings.route
}

@Composable
fun BottomBarRow(
	navController: NavHostController,
) {
	val tabList = listOf(
		BottomBarRoutes.RECIPES,
		BottomBarRoutes.DISCOVER,
	)
	val navStackBackEntry by navController.currentBackStackEntryAsState()
	val currentDestination = navStackBackEntry?.destination

	Row(
		modifier = Modifier.fillMaxWidth(),
		horizontalArrangement = Arrangement.SpaceAround,
		verticalAlignment = Alignment.CenterVertically
	) {
		tabList.forEach { tab ->
			BottomBarItems(
				tab = tab,
				currentDestination = currentDestination,
				navController = navController
			)
		}
	}
}

@Composable
fun BottomBarItems(
	tab: BottomBarRoutes,
	currentDestination: NavDestination?,
	navController: NavHostController
) {
	val selected = currentDestination?.hierarchy?.any { it.route == tab.route } == true
	val contentColor =
		if (selected) {
			MaterialTheme.colorScheme.primary
		} else MaterialTheme.colorScheme.secondary

	IconButton(
		onClick = { navController.navigate(tab.route) },
		enabled = !selected
	) {
		//TODO: For user profile change to be image if there is one available. Potential save it to local store to prevent unnecessary calls to api.
		Icon(
			painter = painterResource(id = tab.icon),
			contentDescription = tab.route,
			tint = contentColor,
		)
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarRow(
	title: String = stringResource(id = R.string.app_name),
	appState: AppState,
) {
	val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
	CenterAlignedTopAppBar(
		title = { Text(text = title) },
		navigationIcon = {
			if (appState.shouldShowBackButton) {
				IconButton(onClick = {
					appState.navController.popBackStack()
				}) {
					Icon(
						imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
						contentDescription = "Navigate back arrow"
					)
				}
			}
		},
		actions = {
			IconButton(onClick = {
				appState.navController.navigate(ScreenRoutes.Settings.route)
			}) {
				Icon(
					Icons.Default.Settings,
					contentDescription = "Settings button",
					tint = MaterialTheme.colorScheme.primary
				)
			}
		},
		colors = TopAppBarDefaults.topAppBarColors(
			containerColor = MaterialTheme.colorScheme.primaryContainer,
			titleContentColor = MaterialTheme.colorScheme.primary,
		),
		scrollBehavior = scrollBehavior
	)
}
