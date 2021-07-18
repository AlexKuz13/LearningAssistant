package com.example.learningassistant.database.intefaces

import androidx.lifecycle.LiveData
import com.example.learningassistant.models.Chat
import com.example.learningassistant.models.Message
import com.example.learningassistant.models.User

interface DatabaseMessageRepository {


    val allMsgFromChat: LiveData<List<Message>>

    suspend fun sendMessage(message: Message, onSuccess: () -> Unit)

    suspend fun rateUser(user: User, rate: Float, onSuccess: (interlocutor:User) -> Unit)
}