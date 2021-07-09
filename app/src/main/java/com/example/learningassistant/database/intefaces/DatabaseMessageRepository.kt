package com.example.learningassistant.database.intefaces

import androidx.lifecycle.LiveData
import com.example.learningassistant.models.Message

interface DatabaseMessageRepository {

    val allMessages: LiveData<List<Message>>
    suspend fun insertMessage(msg: Message, onSuccess:()->Unit)
}