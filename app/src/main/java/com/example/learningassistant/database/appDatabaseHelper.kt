package com.example.learningassistant.database

import android.net.Uri
import com.example.learningassistant.models.User
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

lateinit var AUTH:FirebaseAuth
lateinit var DB:FirebaseFirestore
lateinit var USER:User
lateinit var UID:String
lateinit var REF_STORAGE_ROOT: StorageReference

const val COLL_USERS="users"

const val FOLDER_PROFILE_IMAGE = "profile_image"

const val CHILD_ID = "id"
const val CHILD_PHONE = "phone"
const val CHILD_FULLNAME = "fullName"
const val CHIlD_INFO = "info"
const val CHILD_PHOTO_URL = "photoUrl"
const val CHILD_STATUS = "status"
const val CHILD_RATING="rating"
const val CHILD_COMPLETEWORKS="completeWorks"


fun initFirebase(){
    AUTH= FirebaseAuth.getInstance()
    DB= FirebaseFirestore.getInstance()
    USER = User()
    UID= AUTH.currentUser?.uid.toString()
    REF_STORAGE_ROOT = FirebaseStorage.getInstance().reference
}

fun initUser(){
    DB.collection(COLL_USERS).document(UID).get()
        .addOnSuccessListener {
            USER=it.toObject(User::class.java) ?: User()
            APP_ACTIVITY.mNavDrawer.updateHeader()
        }
        .addOnFailureListener { showToast(it.message.toString()) }
}


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

inline fun putUrlToDatabase(url:String, crossinline function: () -> Unit){
    DB.collection(COLL_USERS).document(UID).update(CHILD_PHOTO_URL,url)
        .addOnSuccessListener { function() }
        .addOnFailureListener { showToast(it.message.toString()) }
}


