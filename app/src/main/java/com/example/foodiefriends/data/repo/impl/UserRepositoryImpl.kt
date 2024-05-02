package com.example.foodiefriends.data.repo.impl

import com.example.foodiefriends.data.LocalUser
import com.example.foodiefriends.data.UserDao
import com.example.foodiefriends.data.repo.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
	private val userDao: UserDao
) : UserRepository {
	override suspend fun insertUser(user: LocalUser) {
		userDao.userInsert(user)
	}

	override fun getUser(id: Int): Flow<LocalUser?> {
		return userDao.getUser(id)
	}
}