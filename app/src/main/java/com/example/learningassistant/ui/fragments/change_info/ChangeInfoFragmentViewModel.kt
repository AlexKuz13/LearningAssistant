package com.example.learningassistant.ui.fragments.change_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningassistant.data.database.CHIlD_INFO
import com.example.learningassistant.data.database.USER
import com.example.learningassistant.data.database.USER_REPOSITORY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChangeInfoFragmentViewModel(val info: String) :ViewModel(){

    fun changeInfo(onSuccess:()->Unit){
        viewModelScope.launch(Dispatchers.IO) {
            USER_REPOSITORY.updateUser(USER,hashMapOf(CHIlD_INFO to info)) {
                USER.info = info
                onSuccess()
            }
        }
    }
}