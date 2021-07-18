package com.example.learningassistant.database.firebase

import androidx.lifecycle.LiveData
import com.example.learningassistant.database.COLL_CHATS_ROSTER
import com.example.learningassistant.database.DB
import com.example.learningassistant.database.UID
import com.example.learningassistant.models.Chat
import com.example.learningassistant.models.Task
import com.example.learningassistant.utilits.INTERLOCUTOR
import com.example.learningassistant.utilits.showToast
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot

class ChatLiveData:LiveData<List<Chat>>() {

    private lateinit var  listenerData :ListenerRegistration

    private val listener = EventListener<QuerySnapshot>{ snapshot, error ->
        error?.let { showToast(it.message.toString()) }
        snapshot?.let { value = it.toObjects(Chat::class.java) }
    }


    override fun onActive() {
        listenerData = DB.collection(COLL_CHATS_ROSTER).document(UID).collection(INTERLOCUTOR).addSnapshotListener(listener)
        super.onActive()
    }

    override fun onInactive() {
        listenerData.remove()
        super.onInactive()
    }
}