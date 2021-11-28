package com.example.learningassistant.data.database.firebase

import androidx.lifecycle.LiveData
import com.example.learningassistant.data.database.*
import com.example.learningassistant.models.Task
import com.example.learningassistant.utilits.showToast
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot

class MyTaskLiveData : LiveData<List<Task>>() {

    private lateinit var listenerData: ListenerRegistration


    private val listener =
        EventListener<QuerySnapshot> { snapshot, error ->
            error?.let { showToast(it.message.toString()) }
            snapshot?.let { value = it.toObjects(Task::class.java) }
        }

    override fun onActive() {
        listenerData = DB.collection(COLL_TASKS).whereEqualTo(TASK_FROM, UID)
            .orderBy(CHILD_TIMESTAMP)
            .addSnapshotListener(listener)
        super.onActive()
    }

    override fun onInactive() {
        listenerData.remove()
        super.onInactive()
    }
}