package com.example.learningassistant.ui.fragments.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.learningassistant.models.User

@Suppress("UNCHECKED_CAST")
class RegisterViewModelFactory(val user: User) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RegisterFragmentViewModel(user) as T
    }
}