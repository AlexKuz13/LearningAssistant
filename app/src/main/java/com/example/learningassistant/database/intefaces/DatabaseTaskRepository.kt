package com.example.learningassistant.database.intefaces

import androidx.lifecycle.LiveData
import com.example.learningassistant.models.Task

interface DatabaseTaskRepository {

    val allTasks: LiveData<List<Task>>
    suspend fun insertTask(task: Task, onSuccess:()->Unit)
}