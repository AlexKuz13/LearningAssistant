package com.example.learningassistant.ui.fragments.create_tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningassistant.data.database.firebase.AppFirebaseTask
import com.example.learningassistant.models.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTaskFragmentViewModel @Inject constructor(
    private val appFirebaseTask: AppFirebaseTask
) : ViewModel() {

    fun insertTask(task: Task, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            appFirebaseTask.insertTask(task) {
                onSuccess()
            }
        }
    }
}