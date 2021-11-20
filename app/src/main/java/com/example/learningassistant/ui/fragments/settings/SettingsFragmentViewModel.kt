package com.example.learningassistant.ui.fragments.settings

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningassistant.data.DataStoreRepository
import com.example.learningassistant.data.database.CHILD_PHOTO_URL
import com.example.learningassistant.data.database.STORAGE
import com.example.learningassistant.data.database.USER
import com.example.learningassistant.data.database.firebase.AppFirebaseStorage
import com.example.learningassistant.data.database.firebase.AppFirebaseUser
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsFragmentViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val appFirebaseUser: AppFirebaseUser
) : ViewModel() {


    var readLangCodeAndId = dataStoreRepository.readLangCodeAndId

    init {
        STORAGE = AppFirebaseStorage()
    }

    fun updatePhoto(uri: Uri, path: StorageReference, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            STORAGE.putImageToStorage(uri, path)
            STORAGE.getUrlFromStorage(path) {
                viewModelScope.launch(Dispatchers.IO) {
                    appFirebaseUser.updateUser(USER, hashMapOf(CHILD_PHOTO_URL to USER.photoUrl)) {
                        onSuccess()
                    }
                }
            }
        }
    }


    fun saveLangCodeAndId(
        langCode: String,
        langRBId: Int,
        language: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveLangCodeAndId(
                langCode,
                langRBId,
                language
            )
        }
    }
}