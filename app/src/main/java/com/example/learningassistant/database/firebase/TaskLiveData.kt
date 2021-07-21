package com.example.learningassistant.database.firebase

import androidx.lifecycle.LiveData
import com.example.learningassistant.database.*
import com.example.learningassistant.models.Task
import com.example.learningassistant.models.User
import com.example.learningassistant.utilits.showToast
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot

class TaskLiveData: LiveData<List<Task>>() {

    private lateinit var listenerData : ListenerRegistration

    private val listener =
        EventListener<QuerySnapshot> { snapshot, error ->
            error?.let { showToast(it.message.toString()) }
            snapshot?.let { value = it.toObjects(Task::class.java) }
        }

    override fun onActive() {
        listenerData = DB.collection(COLL_TASKS).orderBy(CHILD_TIMESTAMP).addSnapshotListener(listener)
        super.onActive()
    }

    override fun onInactive() {
        listenerData.remove()
        super.onInactive()
    }
}