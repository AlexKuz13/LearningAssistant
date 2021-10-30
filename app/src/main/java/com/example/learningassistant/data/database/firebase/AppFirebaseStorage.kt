package com.example.learningassistant.data.database.firebase

import android.net.Uri
import com.example.learningassistant.data.database.USER
import com.example.learningassistant.data.database.intefaces.DatabaseStorage
import com.example.learningassistant.utilits.showToast
import com.google.firebase.storage.StorageReference

class AppFirebaseStorage : DatabaseStorage {


    override suspend fun putImageToStorage(
        uri: Uri,
        path: StorageReference,
    ) {
        path.putFile(uri)
            .addOnFailureListener { showToast(it.message.toString()) }
    }

    override suspend fun getUrlFromStorage(path: StorageReference, onSuccess: () -> Unit) {
        path.downloadUrl
            .addOnSuccessListener {
                USER.photoUrl = it.toString()
                onSuccess()
            }
            .addOnFailureListener {
                showToast(it.message.toString())
            }
    }
}