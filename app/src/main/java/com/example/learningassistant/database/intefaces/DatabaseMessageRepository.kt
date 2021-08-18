package com.example.learningassistant.database.intefaces

import androidx.lifecycle.LiveData
import com.example.learningassistant.models.Message

interface DatabaseMessageRepository {


    val allMsgFromChat: LiveData<List<Message>>

    suspend fun sendMessage(message: Message, onSuccess: () -> Unit)

    suspend fun rateUser(rate: Float, onSuccess: () -> Unit)
}