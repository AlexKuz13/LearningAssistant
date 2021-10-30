package com.example.learningassistant.data.database.intefaces

import androidx.lifecycle.LiveData
import com.example.learningassistant.models.Chat

interface DatabaseChatRepository {

    val allChats: LiveData<List<Chat>>

    suspend fun addChat(chat: Chat, onSuccess: () -> Unit)
}