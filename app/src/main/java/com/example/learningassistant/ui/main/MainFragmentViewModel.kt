package com.example.learningassistant.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.learningassistant.database.TASK_REPOSITORY
import com.example.learningassistant.database.USER_REPOSITORY
import com.example.learningassistant.database.firebase.AppFirebaseTask
import com.example.learningassistant.database.firebase.AppFirebaseUser
import com.example.learningassistant.models.Task
import com.example.learningassistant.models.User


class MainFragmentViewModel():ViewModel() {

    val currentUser: LiveData<User>
    val listTasks :LiveData<List<Task>>

    init {
        USER_REPOSITORY = AppFirebaseUser()
        TASK_REPOSITORY = AppFirebaseTask()
        currentUser = USER_REPOSITORY.currentUser
        listTasks = TASK_REPOSITORY.allTasks
    }
}