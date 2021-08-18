package com.example.learningassistant.database.firebase

import androidx.lifecycle.LiveData
import com.example.learningassistant.database.CHILD_TIMESTAMP
import com.example.learningassistant.database.COLL_MESSAGES
import com.example.learningassistant.database.DB
import com.example.learningassistant.database.UID
import com.example.learningassistant.models.Message
import com.example.learningassistant.utilits.showToast
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot

class MessageLiveData(private val interlocutorId: String) : LiveData<List<Message>>() {

    private lateinit var listenerData: ListenerRegistration

    private val listener =
        EventListener<QuerySnapshot> { snapshot, error ->
            error?.let { showToast(it.message.toString()) }
            snapshot?.let { value = it.toObjects(Message::class.java) }
        }

    override fun onActive() {
        listenerData =
            DB.collection(COLL_MESSAGES).document(UID).collection(interlocutorId)
                .orderBy(CHILD_TIMESTAMP)
                .addSnapshotListener(listener)
        super.onActive()
    }

    override fun onInactive() {
        listenerData.remove()
        super.onInactive()
    }
}