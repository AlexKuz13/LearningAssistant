package com.example.learningassistant.ui.fragments.messages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class MessagesViewModelFactory(private val interlocutor: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MessagesFragmentViewModel(interlocutor) as T
    }
}