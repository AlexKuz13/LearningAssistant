package com.example.learningassistant.data.database.firebase

import androidx.lifecycle.LiveData
import com.example.learningassistant.data.database.*
import com.example.learningassistant.models.Task
import com.example.learningassistant.utilits.showToast
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot

class TaskLiveData(filter: List<String>) : LiveData<List<Task>>() {

    private lateinit var listenerData: ListenerRegistration
    private var schoolSubject = filter[0]
    private var schoolClass = filter[1]

    private val listener =
        EventListener<QuerySnapshot> { snapshot, error ->
            error?.let { showToast(it.message.toString()) }
            snapshot?.let { value = it.toObjects(Task::class.java) }
        }

    override fun onActive() {
        if (schoolClass == "Все" && schoolSubject == "Все") {
            listenerData =
                DB.collection(COLL_TASKS).orderBy(CHILD_TIMESTAMP).addSnapshotListener(listener)
        } else if (schoolClass == "Все" && schoolSubject != "Все") {
            listenerData =
                DB.collection(COLL_TASKS).whereEqualTo(TASK_SCHOOL_SUBJECT, schoolSubject)
                    .orderBy(CHILD_TIMESTAMP)
                    .addSnapshotListener(listener)
        } else if (schoolClass != "Все" && schoolSubject == "Все") {
            listenerData = DB.collection(COLL_TASKS).whereEqualTo(TASK_SCHOOL_CLASS, schoolClass)
                .orderBy(CHILD_TIMESTAMP)
                .addSnapshotListener(listener)
        } else
            listenerData =
                DB.collection(COLL_TASKS).whereEqualTo(TASK_SCHOOL_SUBJECT, schoolSubject)
                    .whereEqualTo(TASK_SCHOOL_CLASS, schoolClass).orderBy(CHILD_TIMESTAMP)
                    .addSnapshotListener(listener)
        super.onActive()
    }

    override fun onInactive() {
        listenerData.remove()
        super.onInactive()
    }
}