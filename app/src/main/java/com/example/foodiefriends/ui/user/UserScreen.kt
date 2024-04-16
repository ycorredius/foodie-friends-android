package com.example.foodiefriends.ui.user

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.foodiefriends.R
import com.example.foodiefriends.ui.NavigationDestination

object UserDestination: NavigationDestination{
	override val route = "userProfile"
	override val titleRes = R.string.user_profile
}

@Composable
fun UserScreen(){
	Text(text = "User profile screen")
}