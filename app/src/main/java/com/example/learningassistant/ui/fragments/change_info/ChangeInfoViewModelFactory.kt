package com.example.learningassistant.ui.fragments.change_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class ChangeInfoViewModelFactory(private val info:String):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChangeInfoFragmentViewModel(info) as T
    }
}