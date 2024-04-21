package com.example.foodiefriends.ui

sealed interface Errors{
	data object ServerError: Errors
	data object Other: Errors
	data object None: Errors
}