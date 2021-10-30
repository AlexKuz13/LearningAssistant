package com.example.learningassistant.ui.fragments.create_tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningassistant.data.database.TASK_REPOSITORY
import com.example.learningassistant.models.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateTaskFragmentViewModel(val task : Task) : ViewModel() {

    fun insertTask(onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            TASK_REPOSITORY.insertTask(task) {
                onSuccess()
            }
        }
    }
}