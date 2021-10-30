package com.example.learningassistant.data.database.intefaces

import android.net.Uri
import com.google.firebase.storage.StorageReference

interface DatabaseStorage {

    suspend fun putImageToStorage(uri: Uri, path: StorageReference)

    suspend fun getUrlFromStorage( path: StorageReference,onSuccess:()->Unit)
}