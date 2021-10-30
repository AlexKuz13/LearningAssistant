package com.example.learningassistant.ui.fragments.chats

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.learningassistant.data.database.CHAT_REPOSITORY
import com.example.learningassistant.data.database.firebase.AppFirebaseChat
import com.example.learningassistant.models.Chat

class ChatsFragmentViewModel : ViewModel() {

    val listChats: LiveData<List<Chat>>

    init {
        CHAT_REPOSITORY = AppFirebaseChat("")
        listChats = CHAT_REPOSITORY.allChats
    }

}