package com.example.learningassistant.data.database.firebase

import androidx.lifecycle.LiveData
import com.example.learningassistant.data.database.COLL_USERS
import com.example.learningassistant.data.database.DB
import com.example.learningassistant.data.database.intefaces.DatabaseUserRepository
import com.example.learningassistant.models.User
import com.example.learningassistant.utilits.showToast
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class AppFirebaseUser @Inject constructor() : DatabaseUserRepository {

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