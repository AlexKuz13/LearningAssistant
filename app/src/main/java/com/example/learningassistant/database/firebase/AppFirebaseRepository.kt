package com.example.learningassistant.database.firebase

import com.example.learningassistant.database.AUTH
import com.example.learningassistant.database.DB
import com.example.learningassistant.database.REF_STORAGE_ROOT
import com.example.learningassistant.database.UID
import com.example.learningassistant.database.intefaces.DatabaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class AppFirebaseRepository : DatabaseRepository {

    init {
        AUTH = FirebaseAuth.getInstance()
    }


    override fun connectToDatabase(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit
    ) {
        AUTH.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                initRefs()
                onSuccess()
            }
            .addOnFailureListener {
                onFail(it.message.toString())
            }
    }

    override fun createDatabase(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit
    ) {
        AUTH.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                initRefs()
                onSuccess()
            }
            .addOnFailureListener {
                onFail(it.message.toString())
            }
    }


    private fun initRefs() {
        UID = AUTH.currentUser?.uid.toString()
        REF_STORAGE_ROOT = FirebaseStorage.getInstance().reference
        DB = FirebaseFirestore.getInstance()
    }

    override fun signOut() {
        AUTH.signOut()
    }
}