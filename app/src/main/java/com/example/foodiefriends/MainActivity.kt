package com.example.foodiefriends

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodiefriends.network.ConnectionStatus
import com.example.foodiefriends.ui.AppNavHost
import com.example.foodiefriends.ui.reusables.UnavailableNetwork
import com.example.foodiefriends.ui.reusables.rememberConnectionStatus
import com.example.foodiefriends.ui.theme.FoodieFriendsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	@OptIn(ExperimentalCoroutinesApi::class)
	@RequiresApi(Build.VERSION_CODES.O)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			FoodieFriendsTheme {
				val appState = rememberAppState()
				val connectionStatus by rememberConnectionStatus()
				val isConnected by remember(connectionStatus) {
					derivedStateOf { connectionStatus == ConnectionStatus.Available }
				}

				// A surface container using the 'background' color from the theme
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colorScheme.background
				) {
					if (isConnected) {
						Scaffold(
							bottomBar = {
								if (appState.shouldBottomBarShow) BottomAppBar(
									containerColor = MaterialTheme.colorScheme.primaryContainer,
									contentPadding = PaddingValues(horizontal = 20.dp),
									modifier = Modifier
										.height(55.dp)
								) {
									BottomBarRow(navController = appState.navController)
								}
							}
						) { padding ->
							Box(modifier = Modifier.padding(padding)) {
								AppNavHost(appState)
							}
						}
					} else {
						UnavailableNetwork()
					}
				}
			}
		}
	}
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
	Text(
		text = "Hello $name!",
		modifier = modifier
	)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
	FoodieFriendsTheme {
		Greeting("Android")
	}
}