package com.example.learningassistant.ui.fragments.enter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class EnterViewModelFactory(val email: String, val password: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EnterFragmentViewModel(email, password) as T
    }
}