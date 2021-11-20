package com.example.learningassistant.ui.fragments.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningassistant.data.database.UID
import com.example.learningassistant.data.database.firebase.AppFirebaseRepository
import com.example.learningassistant.data.database.firebase.AppFirebaseUser
import com.example.learningassistant.models.User
import com.example.learningassistant.utilits.showToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterFragmentViewModel @Inject constructor(
    private val appFirebaseUser: AppFirebaseUser,
    private val appFirebaseRepository: AppFirebaseRepository
) : ViewModel() {

    fun createDatabase(user: User, onSuccess: () -> Unit) {
        appFirebaseRepository.createDatabase(email = user.email, password = user.password, {
            viewModelScope.launch(Dispatchers.IO) {
                user.id = UID
                appFirebaseUser.insertUser(user)
                {
                    appFirebaseRepository.connectToDatabase(
                        email = user.email,
                        password = user.password,
                        { onSuccess() },
                        { showToast(it) })
                }
            }
        }, { showToast(it) })
    }
}