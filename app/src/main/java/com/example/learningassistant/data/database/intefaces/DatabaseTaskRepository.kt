package com.example.learningassistant.data.database.intefaces

import androidx.lifecycle.LiveData
import com.example.learningassistant.models.Task
import dagger.hilt.android.scopes.ViewModelScoped

@ViewModelScoped
interface DatabaseTaskRepository {

    fun allTasks(filter: List<String>): LiveData<List<Task>>
    fun allMyTasks(): LiveData<List<Task>>
    suspend fun insertTask(task: Task, onSuccess: () -> Unit)
    suspend fun deleteAllMyTasks(onSuccess: () -> Unit)
    suspend fun deleteMyTask(task: Task)
}