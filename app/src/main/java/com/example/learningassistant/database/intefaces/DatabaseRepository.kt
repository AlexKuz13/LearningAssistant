package com.example.learningassistant.database.intefaces

import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import com.example.learningassistant.models.Chat
import com.example.learningassistant.models.Message
import com.example.learningassistant.models.Task
import com.example.learningassistant.models.User

interface DatabaseRepository {

    fun connectToDatabase(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit
    )

    fun createDatabase(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit
    )

    fun signOut()

}