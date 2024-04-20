package com.example.foodiefriends.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

sealed class ConnectionStatus {
	data object Available : ConnectionStatus()
	data object Unavailable : ConnectionStatus()
}

private fun networkCallback(callback: (ConnectionStatus) -> Unit): ConnectivityManager.NetworkCallback {
	return object : ConnectivityManager.NetworkCallback() {
		override fun onAvailable(network: Network) {
			callback(ConnectionStatus.Available)
		}

		override fun onUnavailable() {
			callback(ConnectionStatus.Unavailable)
		}
	}
}

private fun getCurrentConnectivityState(connectivityManager: ConnectivityManager): ConnectionStatus {
	val network = connectivityManager.activeNetwork

	val connected = connectivityManager.getNetworkCapabilities(network)
		?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false

	return if (connected) ConnectionStatus.Available else ConnectionStatus.Unavailable
}

fun Context.observeConnectivityAsFlow(): Flow<ConnectionStatus> = callbackFlow {
	val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
	val callback = networkCallback { connectionStatus ->
		trySendBlocking(connectionStatus)
	}

	val networkRequest = NetworkRequest.Builder()
		.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
		.build()

	connectivityManager.registerNetworkCallback(networkRequest, callback)

	val currentState = getCurrentConnectivityState(connectivityManager)
	trySend(currentState)

	awaitClose {
		connectivityManager.unregisterNetworkCallback(callback)
	}
}

val Context.currentConnectivityState: ConnectionStatus
	get() {
		val connectivityManager =
			getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
		return getCurrentConnectivityState(connectivityManager)
	}