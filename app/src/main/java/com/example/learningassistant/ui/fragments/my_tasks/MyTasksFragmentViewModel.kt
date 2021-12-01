package com.example.learningassistant.ui.fragments.my_tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningassistant.data.database.firebase.AppFirebaseTask
import com.example.learningassistant.models.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyTasksFragmentViewModel @Inject constructor(
    private val appFirebaseTask: AppFirebaseTask
) : ViewModel() {


    fun listMyTasks(): LiveData<List<Task>> {
        return appFirebaseTask.allMyTasks()
    }

    fun deleteAllMyTasks(onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            appFirebaseTask.deleteAllMyTasks {
                onSuccess()
            }
        }
    }

    fun deleteMyTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            appFirebaseTask.deleteMyTask(task)
        }
    }
}