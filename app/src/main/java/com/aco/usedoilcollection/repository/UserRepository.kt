package com.aco.usedoilcollection.repository

import com.aco.usedoilcollection.database.dao.UserDao
import com.aco.usedoilcollection.database.entities.User

class UserRepository(private val userDao: UserDao) {

    suspend fun insert(user: User): Long {
        return userDao.insert(user)
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    suspend fun setLoginStatus(userId: Int, isLoggedIn: Boolean) {
        userDao.setLoginStatus(userId, isLoggedIn)
    }

    suspend fun getLoggedInUser(): User? {
        return userDao.getLoggedInUser()
    }

    suspend fun deleteAllUsers() {
        userDao.deleteAllUsers()
    }
}
