package com.example.learningassistant.database.firebase

import androidx.lifecycle.LiveData
import com.example.learningassistant.database.CHAT
import com.example.learningassistant.database.COLL_CHATS_ROSTER
import com.example.learningassistant.database.DB
import com.example.learningassistant.database.UID
import com.example.learningassistant.database.intefaces.DatabaseChatRepository
import com.example.learningassistant.models.Chat
import com.example.learningassistant.models.Message
import com.example.learningassistant.models.User
import com.example.learningassistant.utilits.INTERLOCUTOR
import com.example.learningassistant.utilits.showToast

class AppFirebaseChat:DatabaseChatRepository {

    private val liveDataChat = ChatLiveData()

    override val allChats: LiveData<List<Chat>>
        get() = liveDataChat

    override suspend fun addChat(interlocutor:User,chat :Chat, onSuccess: () -> Unit) {
        CHAT.id = interlocutor.id
        DB.collection(COLL_CHATS_ROSTER).document(UID).collection(INTERLOCUTOR).document(interlocutor.id)
            .set(chat).addOnSuccessListener {
                CHAT.id= UID
                DB.collection(COLL_CHATS_ROSTER).document(interlocutor.id).collection(INTERLOCUTOR)
                    .document(UID)
                    .set(chat)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener {
                        showToast(it.message.toString())
                    }
            }
            .addOnFailureListener { showToast(it.message.toString()) }
    }
}