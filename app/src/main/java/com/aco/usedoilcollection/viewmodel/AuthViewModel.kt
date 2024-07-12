package com.aco.usedoilcollection.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aco.usedoilcollection.database.entities.User
import com.aco.usedoilcollection.repository.UserRepository
import kotlinx.coroutines.launch
import java.security.MessageDigest

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {

    private var currentUser: User? = null

    fun register(email: String, password: String, name: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val existingUser = userRepository.getUserByEmail(email)
            if (existingUser != null) {
                onError("User already exists")
            } else {
                val hashedPassword = hashPassword(password)
                val newUser = User(email = email, passwordHash = hashedPassword, name = name, isLoggedIn = true)
                val userId = userRepository.insert(newUser)
                if (userId > 0) {
                    currentUser = newUser.copy(id = userId.toInt())
                    onSuccess()
                } else {
                    onError("Error registering user")
                }
            }
        }
    }


    fun login(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val user = userRepository.getUserByEmail(email)
            if (user == null) {
                onError("User not found")
            } else if (user.passwordHash != hashPassword(password)) {
                onError("Incorrect password")
            } else {
                userRepository.setLoginStatus(user.id, true)
                currentUser = user
                onSuccess()
            }
        }
    }

    fun logout(userId: Int) {
        viewModelScope.launch {
            userRepository.setLoginStatus(userId, false)
            currentUser = null
        }
    }

    fun setCurrentUser(user: User) {
        currentUser = user
    }

    fun getCurrentUser(): User? {
        return currentUser
    }

    private fun hashPassword(password: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(password.toByteArray())
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}
