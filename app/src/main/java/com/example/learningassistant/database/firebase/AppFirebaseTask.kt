package com.example.learningassistant.database.firebase

import androidx.lifecycle.LiveData
import com.example.learningassistant.database.COLL_TASKS
import com.example.learningassistant.database.COLL_USERS
import com.example.learningassistant.database.DB
import com.example.learningassistant.database.TASK
import com.example.learningassistant.database.intefaces.DatabaseTaskRepository
import com.example.learningassistant.models.Task
import com.example.learningassistant.utilits.showToast

class AppFirebaseTask : DatabaseTaskRepository {

    private val livedataUser = TaskLiveData()

    override val allTasks: LiveData<List<Task>>
        get() = livedataUser

    override suspend fun insertTask(task: Task, onSuccess: () -> Unit) {
        val taskKey = DB.collection(COLL_TASKS).document().id
        DB.collection(COLL_TASKS).document(taskKey).set(task)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { showToast(it.message.toString()) }
    }
}