package com.example.learningassistant.ui.fragments.messages

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningassistant.data.database.CHAT_REPOSITORY
import com.example.learningassistant.data.database.MESSAGE_REPOSITORY
import com.example.learningassistant.data.database.firebase.AppFirebaseChat
import com.example.learningassistant.data.database.firebase.AppFirebaseMessage
import com.example.learningassistant.models.Chat
import com.example.learningassistant.models.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessagesFragmentViewModel(interlocutorId: String) : ViewModel() {

    val listMessages: LiveData<List<Message>>

    init {
        MESSAGE_REPOSITORY = AppFirebaseMessage(interlocutorId)
        CHAT_REPOSITORY = AppFirebaseChat(interlocutorId)
        listMessages = MESSAGE_REPOSITORY.allMsgFromChat
    }

    fun sendMessage(message: Message, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            MESSAGE_REPOSITORY.sendMessage(message) {
                onSuccess()
            }
        }
    }


    fun addChat(chat: Chat, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            CHAT_REPOSITORY.addChat(chat) {
                onSuccess()
            }
        }
    }
}