package com.example.learningassistant.ui.fragments.enter

import androidx.lifecycle.ViewModel
import com.example.learningassistant.data.database.firebase.AppFirebaseRepository
import com.example.learningassistant.utilits.showToast
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EnterFragmentViewModel @Inject constructor(
    private val appFirebaseRepository: AppFirebaseRepository
) : ViewModel() {

    fun initDatabase(email: String, password: String, onSuccess: () -> Unit) {
        appFirebaseRepository.connectToDatabase(
            email = email,
            password = password,
            { onSuccess() },
            { showToast(it) })
    }
}