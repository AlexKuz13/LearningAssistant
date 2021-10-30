package com.example.learningassistant.ui.fragments.change_name

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningassistant.data.database.CHILD_FULLNAME
import com.example.learningassistant.data.database.USER
import com.example.learningassistant.data.database.USER_REPOSITORY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChangeNameFragmentViewModel(val fullName: String) : ViewModel() {

    fun changeName(onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            USER_REPOSITORY.updateUser(USER, hashMapOf(CHILD_FULLNAME to fullName)) {
                USER.fullName = fullName
                onSuccess()
            }
        }
    }
}