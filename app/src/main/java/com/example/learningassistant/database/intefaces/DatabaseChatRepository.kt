package com.example.learningassistant.database.intefaces

import androidx.lifecycle.LiveData
import com.example.learningassistant.models.Chat
import com.example.learningassistant.models.User

interface DatabaseChatRepository {

    val allChats: LiveData<List<Chat>>
    suspend fun insertChat(chat: Chat, onSuccess:()->Unit)

    suspend fun rateUser(user: User, rate: Float, onSuccess:()->Unit)
}