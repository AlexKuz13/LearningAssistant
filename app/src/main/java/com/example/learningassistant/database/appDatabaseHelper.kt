package com.example.learningassistant.database

import com.example.learningassistant.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

lateinit var AUTH:FirebaseAuth
lateinit var DB:FirebaseFirestore
lateinit var USER:User
lateinit var UID:String

const val COLL_USERS="users"

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
}

fun initUser(){
    DB.collection(COLL_USERS).document(UID).get()
        .addOnSuccessListener {
            USER=it.toObject(User::class.java) ?: User()
        }
}