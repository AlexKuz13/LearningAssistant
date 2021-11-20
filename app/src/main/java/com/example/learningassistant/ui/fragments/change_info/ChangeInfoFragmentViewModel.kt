package com.example.learningassistant.ui.fragments.change_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningassistant.data.database.CHIlD_INFO
import com.example.learningassistant.data.database.USER
import com.example.learningassistant.data.database.firebase.AppFirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeInfoFragmentViewModel @Inject constructor(
    private val appFirebaseUser: AppFirebaseUser
) : ViewModel() {

    fun changeInfo(info: String, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            appFirebaseUser.updateUser(USER, hashMapOf(CHIlD_INFO to info)) {
                USER.info = info
                onSuccess()
            }
        }
    }
}