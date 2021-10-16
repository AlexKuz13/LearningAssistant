package com.example.learningassistant.database

import android.net.Uri
import com.example.learningassistant.models.Message
import com.example.learningassistant.utilits.showToast
import com.google.firebase.firestore.FieldValue
import com.google.firebase.storage.StorageReference


//fun initFirebase() {
//    AUTH = FirebaseAuth.getInstance()
//    DB = FirebaseFirestore.getInstance()
//    USER = User()
//    UID = AUTH.currentUser?.uid.toString()
//    REF_STORAGE_ROOT = FirebaseStorage.getInstance().reference
//}

/*inline fun initUser(crossinline function: () -> Unit) {
    DB.collection(COLL_USERS).document(UID).get()
        .addOnSuccessListener {
            USER = it.toObject(User::class.java) ?: User()
            function()
        }
        .addOnFailureListener { showToast(it.message.toString()) }
}*/


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


//fun sendTask(
//    topic: String,
//    description: String,
//    uid: String,
//    typeDes: String,
//    function: () -> Unit
//) {
//    TASK = Task()
//    TASK.topic = topic
//    TASK.description = description
//    TASK.from = uid
//    TASK.type_des = typeDes
//    TASK.timeStamp = FieldValue.serverTimestamp()
//    val taskKey = DB.collection(COLL_USERS).document().id
//    DB.collection(COLL_TASKS).document(taskKey).set(TASK)
//        .addOnSuccessListener {
//            showToast("Заявка размещена")
//            function()
//        }
//        .addOnFailureListener { showToast(it.message.toString()) }
//}

fun sendMessageAsImage(receivingUserId: String, it: String, messageKey: String) {
    MESSAGE = Message()
    MESSAGE.from = UID
    MESSAGE.imageUrl = it
    MESSAGE.timeStamp = FieldValue.serverTimestamp()
    MESSAGE.type_mes = TYPE_IMAGE
    DB.collection(COLL_MESSAGES).document(UID).collection(receivingUserId).document(messageKey)
        .set(MESSAGE).addOnSuccessListener {
            DB.collection(COLL_MESSAGES).document(receivingUserId).collection(UID)
                .document(messageKey)
                .set(MESSAGE).addOnFailureListener {
                    showToast(it.message.toString())
                }
        }
        .addOnFailureListener { showToast(it.message.toString()) }
}

//fun sendMessage(
//    message: String,
//    receivingUserId: String,
//    messageKey: String,
//    function: () -> Unit
//) {
//    CHAT=Chat()
//    CHAT.last_message=message
//    CHAT.timeStamp=FieldValue.serverTimestamp()
//    MESSAGE = Message()
//    MESSAGE.from = UID
//    MESSAGE.text = message
//    MESSAGE.timeStamp = FieldValue.serverTimestamp()
//    MESSAGE.type_mes = TYPE_TEXT
//    DB.collection(COLL_MESSAGES).document(UID).collection(receivingUserId).document(messageKey)
//        .set(MESSAGE).addOnSuccessListener {
//            DB.collection(COLL_MESSAGES).document(receivingUserId).collection(UID)
//                .document(messageKey)
//                .set(MESSAGE).addOnSuccessListener { function() }
//                .addOnFailureListener {
//                    showToast(it.message.toString())
//                }
//        }
//        .addOnFailureListener { showToast(it.message.toString()) }
//}

//fun rateUser(user: User, rate: Float, function: () -> Unit) {
//    RATING=Rating()
//    getFields(user, rate) {
//        saveFields(user) {
//            function()
//        }
//    }
//}
//
//
//fun getFields(user: User, rate: Float,  function: () -> Unit) {
//
//    DB.collection(COLL_RATINGS).document(user.id).get()
//        .addOnSuccessListener {
//            RATING.rating_sum = it["rating_sum"] as Double
//            DB.collection(COLL_USERS).document(user.id).get()
//                .addOnSuccessListener {
//                    val UserAnother = it.toObject(User::class.java) ?: User()
//                    RATING.complete_works = UserAnother.completeWorks
//                    RATING.rating_sum += rate
//                    RATING.complete_works++
//                    function()
//                }
//                .addOnFailureListener { showToast(it.message.toString()) }
//        }
//        .addOnFailureListener { showToast(it.message.toString()) }
//}
//
//fun saveFields(user: User,function: () -> Unit) {
//    val hashMap = hashMapOf<String, Any>()
//    hashMap[CHILD_RATING]=RATING.rating_sum/ RATING.complete_works
//    hashMap[CHILD_COMPLETEWORKS]= RATING.complete_works
//
//    DB.collection(COLL_USERS).document(user.id).update(hashMap)
//        .addOnSuccessListener {
//            val map = hashMapOf("rating_sum" to RATING.rating_sum)
//            DB.collection(COLL_RATINGS).document(user.id).set(map)
//                .addOnSuccessListener { function() }
//                .addOnFailureListener {showToast(it.message.toString()) }
//        }
//        .addOnFailureListener {showToast(it.message.toString())  }
//}
//
//fun addToRoster(human_id: String) {
//    CHAT.id=human_id
//    DB.collection(COLL_CHATS_ROSTER).document(UID).collection("interlocutor").document(human_id)
//        .set(CHAT).addOnSuccessListener {
//            CHAT.id=UID
//            DB.collection(COLL_CHATS_ROSTER).document(human_id).collection("interlocutor")
//                .document(UID)
//                .set(CHAT)
//                .addOnFailureListener {
//                    showToast(it.message.toString())
//                }
//        }
//        .addOnFailureListener { showToast(it.message.toString()) }
//}


