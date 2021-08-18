package com.example.learningassistant.ui.fragments.rating

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class RatingViewModelFactory(val interlocutor: String, val rating: Float) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RatingFragmentViewModel(interlocutor, rating) as T
    }
}