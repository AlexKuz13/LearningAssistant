package com.example.learningassistant.data.database

import android.net.Uri
import com.example.learningassistant.utilits.showToast
import com.google.firebase.storage.StorageReference


inline fun putImageToStorage(uri: Uri, path: StorageReference, crossinline function: () -> Unit) {
    path.putFile(uri)
        .addOnSuccessListener { function() }
        .addOnFailureListener { showToast(it.message.toString()) }
}

inline fun getUrlFromStorage(path: StorageReference, crossinline function: (url: String) -> Unit) {
    path.downloadUrl
        .addOnSuccessListener { function(it.toString()) }
        .addOnFailureListener { showToast(it.message.toString()) }
}

//inline fun putUrlToDatabase(url: String, crossinline function: () -> Unit) {
//    DB.collection(COLL_USERS).document(UID).update(CHILD_PHOTO_URL, url)
//        .addOnSuccessListener { function() }
//        .addOnFailureListener { showToast(it.message.toString()) }
//}





