package com.example.learningassistant.database

import android.net.Uri
import com.example.learningassistant.models.Message
import com.example.learningassistant.models.Task
import com.example.learningassistant.models.User
import com.example.learningassistant.utilits.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


lateinit var AUTH:FirebaseAuth
lateinit var DB:FirebaseFirestore
lateinit var USER:User
lateinit var TASK:Task
lateinit var MESSAGE:Message
lateinit var UID:String
lateinit var REF_STORAGE_ROOT: StorageReference

const val COLL_USERS="users"
const val COLL_TASKS="tasks"
const val COLL_MESSAGES="messages"
const val COLL_MESSAGES_ID="messages_id"

const val FOLDER_PROFILE_IMAGE = "profile_image"

const val TYPE_TEXT = "text"
const val TYPE_IMAGE="image"

const val CHILD_ID = "id"
const val CHILD_PHONE = "phone"
const val CHILD_FULLNAME = "fullName"
const val CHIlD_INFO = "info"
const val CHILD_PHOTO_URL = "photoUrl"
const val CHILD_STATUS = "status"
const val CHILD_RATING="rating"
const val CHILD_COMPLETEWORKS="completeWorks"
const val CHILD_TOPIC="topic"
const val CHILD_FROM="from"
const val CHILD_DESCRIPTION="description"
const val CHILD_TYPE_DES="type_des"
const val CHILD_TIMESTAMP="timeStamp"

fun initFirebase(){
    AUTH= FirebaseAuth.getInstance()
    DB= FirebaseFirestore.getInstance()
    USER = User()
    UID= AUTH.currentUser?.uid.toString()
    REF_STORAGE_ROOT = FirebaseStorage.getInstance().reference
}

inline fun initUser(crossinline function: () -> Unit){
    DB.collection(COLL_USERS).document(UID).get()
        .addOnSuccessListener {
            USER=it.toObject(User::class.java) ?: User()
            function()
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


fun sendTask(topic: String, description: String, uid: String, typeDes: String, function: () -> Unit) {
    TASK=Task()
    TASK.topic=topic
    TASK.description=description
    TASK.from=uid
    TASK.type_des=typeDes
    TASK.timeStamp=FieldValue.serverTimestamp()
    val taskKey=DB.collection(COLL_USERS).document().id
    DB.collection(COLL_TASKS).document(taskKey).set(TASK)
        .addOnSuccessListener {
            showToast("Заявка размещена")
            function()
        }
        .addOnFailureListener { showToast(it.message.toString()) }
}

fun sendMessageAsImage(receivingUserId: String, it: String, messageKey: String) {
    MESSAGE= Message()
    MESSAGE.from= UID
    MESSAGE.imageUrl=it
    MESSAGE.timeStamp=FieldValue.serverTimestamp()
    MESSAGE.type_mes= TYPE_IMAGE
    DB.collection(COLL_MESSAGES).document(UID).collection(receivingUserId).document(messageKey)
        .set(MESSAGE).addOnSuccessListener {
            DB.collection(COLL_MESSAGES).document(receivingUserId).collection(UID).document(messageKey)
                .set(MESSAGE).addOnFailureListener{
                    showToast(it.message.toString())
                }
        }
        .addOnFailureListener { showToast(it.message.toString()) }
}

fun sendMessage(message: String, receivingUserId: String, messageKey: String, function: () -> Unit) {
    MESSAGE= Message()
    MESSAGE.from= UID
    MESSAGE.text=message
    MESSAGE.timeStamp=FieldValue.serverTimestamp()
    MESSAGE.type_mes= TYPE_TEXT
    DB.collection(COLL_MESSAGES).document(UID).collection(receivingUserId).document(messageKey)
        .set(MESSAGE).addOnSuccessListener {
            DB.collection(COLL_MESSAGES).document(receivingUserId).collection(UID).document(messageKey)
                .set(MESSAGE).addOnSuccessListener { function() }
                .addOnFailureListener{
                    showToast(it.message.toString())
                }
        }
        .addOnFailureListener { showToast(it.message.toString()) }
}



