package com.example.learningassistant.ui.fragments.settings

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningassistant.database.CHILD_PHOTO_URL
import com.example.learningassistant.database.STORAGE
import com.example.learningassistant.database.USER
import com.example.learningassistant.database.USER_REPOSITORY
import com.example.learningassistant.database.firebase.AppFirebaseStorage
import com.example.learningassistant.utilits.showToast
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsFragmentViewModel(val uri: Uri, val path: StorageReference) : ViewModel() {

    init {
        STORAGE = AppFirebaseStorage()
    }

    fun updatePhoto(onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            STORAGE.putImageToStorage(uri, path)
            STORAGE.getUrlFromStorage(path) {
                viewModelScope.launch(Dispatchers.IO) {
                    USER_REPOSITORY.updateUser(USER, hashMapOf(CHILD_PHOTO_URL to USER.photoUrl)) {
                        onSuccess()
                    }
                }
            }
        }
    }


}