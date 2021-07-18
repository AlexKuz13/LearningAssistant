package com.example.learningassistant.database.firebase

import android.net.Uri
import com.example.learningassistant.database.intefaces.DatabaseStorage
import com.example.learningassistant.utilits.showToast
import com.google.firebase.storage.StorageReference

class AppFirebaseStorage:DatabaseStorage {


    override suspend fun putImageToStorage(
        uri: Uri,
        path: StorageReference,
        onSuccess: () -> Unit
    ) {
        path.putFile(uri)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { showToast(it.message.toString()) }
    }

    override suspend fun getUrlFromStorage(
        uri: Uri,
        path: StorageReference
    ): String {
        var url ="empty"
        path.downloadUrl
            .addOnSuccessListener { url = it.toString() }
            .addOnFailureListener { showToast(it.message.toString()) }
        return url
    }
}