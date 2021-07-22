package com.example.learningassistant.ui.fragments.change_name

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class ChangeNameViewModelFactory(val fullName:String):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChangeNameFragmentViewModel(fullName) as T
    }
}