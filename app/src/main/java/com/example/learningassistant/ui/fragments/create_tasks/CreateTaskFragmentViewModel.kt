package com.example.learningassistant.ui.fragments.create_tasks

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningassistant.database.TASK_REPOSITORY
import com.example.learningassistant.models.Task
import com.example.learningassistant.utilits.asTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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