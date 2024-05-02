package com.example.foodiefriends.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun userInsert(user: LocalUser)

	@Query("SELECT * FROM users WHERE id = :id")
	fun getUser(id: Int): Flow<LocalUser>
}