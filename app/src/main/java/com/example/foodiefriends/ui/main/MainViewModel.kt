package com.example.foodiefriends.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.foodiefriends.accessToken
import com.example.foodiefriends.data.AuthRepository
import com.example.foodiefriends.hasAccessToken
import com.example.foodiefriends.printMsg
import com.example.foodiefriends.ui.login.LoginDestination
import com.example.foodiefriends.ui.dashboard.DashboardDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
	@ApplicationContext context: Context
) : ViewModel() {
	private val hasAccessToken = hasAccessToken(context)
	fun onAppStart(openAndPopUp: (String, String) -> Unit){
		printMsg("@@@@ HasAccessToken: $hasAccessToken")
		if(hasAccessToken){
			openAndPopUp(DashboardDestination.route, MainDestination.route)
		} else{
			openAndPopUp(LoginDestination.route, MainDestination.route)
		}
	}
}