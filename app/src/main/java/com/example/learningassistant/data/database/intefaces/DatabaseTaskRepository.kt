package com.example.learningassistant.data.database.intefaces

import androidx.lifecycle.LiveData
import com.example.learningassistant.models.Task

interface DatabaseTaskRepository {

    fun allTasks(filter: List<String>): LiveData<List<Task>>
    suspend fun insertTask(task: Task, onSuccess: () -> Unit)
}