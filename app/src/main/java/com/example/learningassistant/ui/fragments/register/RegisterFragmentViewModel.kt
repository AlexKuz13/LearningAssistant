package com.example.learningassistant.ui.fragments.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningassistant.data.database.REPOSITORY
import com.example.learningassistant.data.database.UID
import com.example.learningassistant.data.database.USER_REPOSITORY
import com.example.learningassistant.data.database.firebase.AppFirebaseRepository
import com.example.learningassistant.data.database.firebase.AppFirebaseUser
import com.example.learningassistant.models.User
import com.example.learningassistant.utilits.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterFragmentViewModel(val user: User) : ViewModel() {

    fun createDatabase(onSuccess: () -> Unit) {
        REPOSITORY = AppFirebaseRepository()
        USER_REPOSITORY = AppFirebaseUser()
        REPOSITORY.createDatabase(email = user.email, password = user.password, {
            viewModelScope.launch(Dispatchers.IO) {
                user.id = UID
                (USER_REPOSITORY as AppFirebaseUser).insertUser(user)
                {
                    REPOSITORY.connectToDatabase(
                        email = user.email,
                        password = user.password,
                        { onSuccess() },
                        { showToast(it) })
                }
            }
        }, { showToast(it) })
    }
}