package com.example.learningassistant.database.intefaces

import androidx.lifecycle.LiveData
import com.example.learningassistant.models.Chat
import com.example.learningassistant.models.Message
import com.example.learningassistant.models.Task
import com.example.learningassistant.models.User

interface DatabaseRepository {

    fun connectToDatabase(onSuccess: () -> Unit,onFail:(String)->Unit){}

    fun signOut(){}

}