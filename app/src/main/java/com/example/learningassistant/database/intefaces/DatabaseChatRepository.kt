package com.example.learningassistant.database.intefaces

import androidx.lifecycle.LiveData
import com.example.learningassistant.models.Chat
import com.example.learningassistant.models.Message
import com.example.learningassistant.models.User

interface DatabaseChatRepository {

    val allChats: LiveData<List<Chat>>

    suspend fun addChat(interlocutor:User,chat:Chat, onSuccess:()->Unit)
}