package com.example.foodiefriends.di

import android.content.Context
import com.example.foodiefriends.network.ApiClient
import com.example.foodiefriends.network.AuthService
import com.example.foodiefriends.network.RecipeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
	@Provides
	@Singleton
	fun provideApiClient() = ApiClient()

	@Provides
	@Singleton
	fun provideRecipeService(
		@ApplicationContext context: Context,
		apiClient: ApiClient
	): RecipeService =
		RecipeService.create(apiClient.getClient(context))

	@Provides
	@Singleton
	fun provideAuthService(
		@ApplicationContext context: Context,
		apiClient: ApiClient
	): AuthService =
		AuthService.create(apiClient.getClient(context, false))
}