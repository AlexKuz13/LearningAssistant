package com.example.learningassistant.data.database.firebase

import androidx.lifecycle.LiveData
import com.example.learningassistant.data.database.COLL_USERS
import com.example.learningassistant.data.database.DB
import com.example.learningassistant.data.database.UID
import com.example.learningassistant.models.User
import com.example.learningassistant.utilits.showToast
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ListenerRegistration

class UserLiveData : LiveData<User>() {

    private lateinit var listenerData :ListenerRegistration

    private val listener =
        EventListener<DocumentSnapshot> { snapshot, error ->
            error?.let { showToast(it.message.toString()) }
            snapshot?.let { value = it.toObject(User::class.java) ?: User() }
        }

    override fun onActive() {
        listenerData = DB.collection(COLL_USERS).document(UID).addSnapshotListener(listener)
        super.onActive()
    }

    override fun onInactive() {
        listenerData.remove()
        super.onInactive()
    }
}