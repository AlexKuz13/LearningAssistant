package com.example.learningassistant.database.firebase

import androidx.lifecycle.LiveData
import com.example.learningassistant.database.COLL_USERS
import com.example.learningassistant.database.DB
import com.example.learningassistant.database.intefaces.DatabaseUserRepository
import com.example.learningassistant.models.User
import com.example.learningassistant.utilits.showToast

class AppFirebaseUser : DatabaseUserRepository {

    private val liveDataUser = UserLiveData()

    override val currentUser: LiveData<User>
        get() = liveDataUser

    override suspend fun insertUser(user: User, onSuccess: () -> Unit) {
        DB.collection(COLL_USERS).document(user.id).set(user)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { showToast(it.message.toString()) }
    }

    override suspend fun updateUser(
        user: User,
        hashMap: HashMap<String,Any>,
        onSuccess: () -> Unit
    ) {
        DB.collection(COLL_USERS).document(user.id).update(hashMap)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { showToast(it.message.toString()) }
    }
}