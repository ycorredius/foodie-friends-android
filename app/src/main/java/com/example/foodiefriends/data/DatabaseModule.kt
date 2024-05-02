package com.example.foodiefriends.data

import android.content.Context
import com.example.foodiefriends.data.repo.UserRepository
import com.example.foodiefriends.data.repo.impl.UserRepositoryImpl
import com.example.foodiefriends.network.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
	@Provides
	@Singleton
	fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
		return AppDatabase.getDatabase(context)
	}

	@Provides
	@Singleton
	fun provideUserDao(database: AppDatabase): UserDao {
		return database.userDao()
	}

	@Provides
	@Singleton
	fun provideUserRepository(
		userDao: UserDao
	): UserRepository {
		return UserRepositoryImpl(userDao)
	}

	@Provides
	@Singleton
	fun provideAuthRepository(
		authService: AuthService,
		@ApplicationContext context: Context,
		userRepository: UserRepository
	): AuthRepository {
		return AuthRepository(authService, context, userRepository = userRepository)
	}
}