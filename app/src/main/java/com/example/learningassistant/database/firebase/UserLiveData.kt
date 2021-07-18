package com.example.learningassistant.database.firebase

import androidx.lifecycle.LiveData
import com.example.learningassistant.database.AUTH
import com.example.learningassistant.database.COLL_USERS
import com.example.learningassistant.database.DB
import com.example.learningassistant.database.UID
import com.example.learningassistant.models.User
import com.example.learningassistant.utilits.showToast
import com.google.firebase.firestore.*

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