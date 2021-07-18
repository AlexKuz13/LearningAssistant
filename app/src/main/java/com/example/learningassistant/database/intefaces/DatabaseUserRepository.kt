package com.example.learningassistant.database.intefaces

import androidx.lifecycle.LiveData
import com.example.learningassistant.models.User

interface DatabaseUserRepository {

    val currentUser: LiveData<User>
    suspend fun insertUser(user: User, onSuccess:()->Unit)
    suspend fun updateUser(user: User, hashMap: HashMap<String,Any>, onSuccess:()->Unit)
}