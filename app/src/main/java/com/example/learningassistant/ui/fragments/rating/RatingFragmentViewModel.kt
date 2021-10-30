package com.example.learningassistant.ui.fragments.rating

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningassistant.data.database.MESSAGE_REPOSITORY
import com.example.learningassistant.data.database.firebase.AppFirebaseMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RatingFragmentViewModel(val interlocutor: String, val rating: Float) : ViewModel() {

    init {
        MESSAGE_REPOSITORY = AppFirebaseMessage(interlocutor)
    }

    fun rateUser(onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            MESSAGE_REPOSITORY.rateUser(rating) {
                onSuccess()
            }
        }
    }
}