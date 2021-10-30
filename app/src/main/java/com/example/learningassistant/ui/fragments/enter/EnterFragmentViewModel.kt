package com.example.learningassistant.ui.fragments.enter

import androidx.lifecycle.ViewModel
import com.example.learningassistant.data.database.REPOSITORY
import com.example.learningassistant.data.database.firebase.AppFirebaseRepository
import com.example.learningassistant.utilits.showToast

class EnterFragmentViewModel(val email:String,val password :String) : ViewModel() {

    fun initDatabase(onSuccess: () -> Unit) {
        REPOSITORY = AppFirebaseRepository()
        REPOSITORY.connectToDatabase(email = email, password = password, { onSuccess() }, { showToast(it) })
    }
}