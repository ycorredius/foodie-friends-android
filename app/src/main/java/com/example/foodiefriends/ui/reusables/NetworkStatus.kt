package com.example.foodiefriends.ui.reusables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import com.example.foodiefriends.network.ConnectionStatus
import com.example.foodiefriends.network.currentConnectivityState
import com.example.foodiefriends.network.observeConnectivityAsFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi

@Composable
fun rememberConnectionStatus(): State<ConnectionStatus> {
	val context = LocalContext.current
	return produceState(initialValue = context.currentConnectivityState) {
		context.observeConnectivityAsFlow().collect {
			value = it
		}
	}
}