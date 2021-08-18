package com.example.learningassistant.database.firebase

import androidx.lifecycle.LiveData
import com.example.learningassistant.database.CHAT
import com.example.learningassistant.database.COLL_CHATS_ROSTER
import com.example.learningassistant.database.DB
import com.example.learningassistant.database.UID
import com.example.learningassistant.database.intefaces.DatabaseChatRepository
import com.example.learningassistant.models.Chat
import com.example.learningassistant.utilits.INTERLOCUTOR
import com.example.learningassistant.utilits.showToast

class AppFirebaseChat(private val interlocutorId: String) : DatabaseChatRepository {

    private val liveDataChat = ChatLiveData()

    override val allChats: LiveData<List<Chat>>
        get() = liveDataChat

    override suspend fun addChat(chat: Chat, onSuccess: () -> Unit) {
        CHAT.id = interlocutorId
        DB.collection(COLL_CHATS_ROSTER).document(UID).collection(INTERLOCUTOR)
            .document(interlocutorId)
            .set(chat).addOnSuccessListener {
                CHAT.id = UID
                DB.collection(COLL_CHATS_ROSTER).document(interlocutorId).collection(INTERLOCUTOR)
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