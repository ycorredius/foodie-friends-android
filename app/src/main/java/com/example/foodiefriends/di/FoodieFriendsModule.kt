package com.example.foodiefriends.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.foodiefriends.data.AuthRepository
import com.example.foodiefriends.data.RecipeRepository
import com.example.foodiefriends.network.RecipeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

const val USER_PREFERENCES_NAME = "user_preferences"

@InstallIn(SingletonComponent::class)
@Module
object FoodieFriendsModule {
	@Provides
	@Singleton
	fun providePreferenceDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
		return PreferenceDataStoreFactory.create(
			corruptionHandler = ReplaceFileCorruptionHandler(
				produceNewData = { emptyPreferences() }
			),
			migrations = listOf(SharedPreferencesMigration(appContext, USER_PREFERENCES_NAME)),
			scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
			produceFile = { appContext.preferencesDataStoreFile(USER_PREFERENCES_NAME) }
		)
	}

	@Provides
	@Singleton
	fun provideRecipeRepository(
		recipeService: RecipeService,
		authRepository: AuthRepository
	): RecipeRepository =
		RecipeRepository(recipeService)
}