package com.example.learningassistant.ui.fragments.change_name

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningassistant.data.database.CHILD_FULLNAME
import com.example.learningassistant.data.database.USER
import com.example.learningassistant.data.database.firebase.AppFirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeNameFragmentViewModel @Inject constructor(
    private val appFirebaseUser: AppFirebaseUser
) : ViewModel() {

    fun changeName(fullName: String, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            appFirebaseUser.updateUser(USER, hashMapOf(CHILD_FULLNAME to fullName)) {
                USER.fullName = fullName
                onSuccess()
            }
        }
    }
}