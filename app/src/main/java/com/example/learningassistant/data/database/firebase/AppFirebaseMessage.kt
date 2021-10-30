package com.example.learningassistant.data.database.firebase

import androidx.lifecycle.LiveData
import com.example.learningassistant.data.database.COLL_MESSAGES
import com.example.learningassistant.data.database.COLL_USERS
import com.example.learningassistant.data.database.DB
import com.example.learningassistant.data.database.UID
import com.example.learningassistant.data.database.intefaces.DatabaseMessageRepository
import com.example.learningassistant.models.Message
import com.example.learningassistant.models.User
import com.example.learningassistant.utilits.showToast

class AppFirebaseMessage(private val interlocutorId: String) : DatabaseMessageRepository {

    private val liveDataMessage = MessageLiveData(interlocutorId)

    override val allMsgFromChat: LiveData<List<Message>>
        get() = liveDataMessage


    override suspend fun sendMessage(message: Message, onSuccess: () -> Unit) {
        val messageKey = DB.collection(COLL_MESSAGES).document().id
        DB.collection(COLL_MESSAGES).document(UID).collection(interlocutorId).document(messageKey)
            .set(message).addOnSuccessListener {
                DB.collection(COLL_MESSAGES).document(interlocutorId).collection(UID)
                    .document(messageKey)
                    .set(message).addOnSuccessListener { onSuccess() }
                    .addOnFailureListener {
                        showToast(it.message.toString())
                    }
            }
            .addOnFailureListener { showToast(it.message.toString()) }
    }


    override suspend fun rateUser(rate: Float, onSuccess: () -> Unit) {
        DB.collection(COLL_USERS).document(interlocutorId).get().addOnSuccessListener {
            val interlocutor = it.toObject(User::class.java) ?: User()
            interlocutor.completeWorks++
            interlocutor.rating_sum += rate
            interlocutor.rating = interlocutor.rating_sum / interlocutor.completeWorks
            onSuccess()
        }
    }


}