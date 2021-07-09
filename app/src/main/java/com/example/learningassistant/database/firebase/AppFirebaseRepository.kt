package com.example.learningassistant.database.firebase

import com.example.learningassistant.database.AUTH
import com.example.learningassistant.database.intefaces.DatabaseRepository
import com.google.firebase.auth.FirebaseAuth

class AppFirebaseRepository: DatabaseRepository {

    init {
        AUTH= FirebaseAuth.getInstance()
    }


    override fun connectToDatabase(onSuccess: () -> Unit, onFail: (String) -> Unit) {
        super.connectToDatabase(onSuccess, onFail)
    }

    override fun signOut() {
        AUTH.signOut()
    }
}