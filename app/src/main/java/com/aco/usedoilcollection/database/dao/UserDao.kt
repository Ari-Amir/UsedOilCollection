package com.aco.usedoilcollection.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aco.usedoilcollection.database.entities.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User): Long

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?

    @Query("UPDATE users SET isLoggedIn = :isLoggedIn WHERE id = :userId")
    suspend fun setLoginStatus(userId: Int, isLoggedIn: Boolean)

    @Query("SELECT * FROM users WHERE isLoggedIn = 1 LIMIT 1")
    suspend fun getLoggedInUser(): User?

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()

    @Query("DELETE FROM sqlite_sequence WHERE name = 'users'")
    suspend fun resetAutoIncrementUsers()
}

