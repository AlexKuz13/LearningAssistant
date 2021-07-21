package com.example.learningassistant.ui.fragments.create_tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.learningassistant.models.Task
import com.example.learningassistant.ui.fragments.enter.EnterFragmentViewModel

@Suppress("UNCHECKED_CAST")
class CreateTaskViewModelFactory(val task: Task) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CreateTaskFragmentViewModel(task) as T
    }
}