package com.example.foodiefriends.ui.reusables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.foodiefriends.R
import com.example.foodiefriends.ui.Errors

@Composable
fun LoadingScreen() {
	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		CircularProgressIndicator(
			modifier = Modifier.width(100.dp),
			color = MaterialTheme.colorScheme.primary,
			trackColor = MaterialTheme.colorScheme.surfaceVariant,
		)
	}
}

@Composable
fun ErrorScreen(
	error: Errors
) {
	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally,
	) {

		Icon(
			painter = painterResource(id = R.drawable.offline),
			contentDescription = "offline Svg",
			tint = MaterialTheme.colorScheme.surfaceTint
		)
		Spacer(modifier = Modifier.height(20.dp))
		when (error) {
			is Errors.ServerError -> Text("Foodie Friend Server is offline!")
			is Errors.Other -> Text("Testing for Science")
			is Errors.None -> Text(text = "Something weird happened. Go check it out!")
		}
	}
}

@Composable
fun UnavailableNetwork(){
	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Icon(
			painter = painterResource(id = R.drawable.offline),
			contentDescription = "offline Svg",
			tint = MaterialTheme.colorScheme.surfaceTint
		)
		Spacer(modifier = Modifier.height(20.dp))
		Text(text = "Network unavailable")
	}
}