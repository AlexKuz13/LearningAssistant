package com.example.learningassistant.data.database.firebase

import androidx.lifecycle.LiveData
import com.example.learningassistant.data.database.COLL_TASKS
import com.example.learningassistant.data.database.DB
import com.example.learningassistant.data.database.intefaces.DatabaseTaskRepository
import com.example.learningassistant.models.Task
import com.example.learningassistant.utilits.showToast
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class AppFirebaseTask @Inject constructor() : DatabaseTaskRepository {


    override fun allTasks(filter: List<String>): LiveData<List<Task>> {
        return TaskLiveData(filter)
    }

    override fun allMyTasks(): LiveData<List<Task>> {
        return MyTaskLiveData()
    }


    override suspend fun insertTask(task: Task, onSuccess: () -> Unit) {
        val taskKey = DB.collection(COLL_TASKS).document().id
        DB.collection(COLL_TASKS).document(taskKey).set(task)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { showToast(it.message.toString()) }
    }
}