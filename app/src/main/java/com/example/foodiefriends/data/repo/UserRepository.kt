package com.example.foodiefriends.data.repo

import com.example.foodiefriends.data.LocalUser
import kotlinx.coroutines.flow.Flow

interface UserRepository {
	suspend fun insertUser(user: LocalUser)
	fun getUser(id: Int): Flow<LocalUser?>
}