package com.example.learningassistant.database.intefaces

import android.net.Uri
import com.example.learningassistant.models.User
import com.google.firebase.storage.StorageReference

interface DatabaseStorage {

    suspend fun putImageToStorage(uri: Uri, path: StorageReference, onSuccess:()->Unit)

    suspend fun getUrlFromStorage(uri: Uri, path: StorageReference, onSuccess:()->Unit)
}