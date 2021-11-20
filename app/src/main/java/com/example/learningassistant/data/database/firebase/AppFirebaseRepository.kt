package com.example.learningassistant.data.database.firebase


import com.example.learningassistant.data.database.DB
import com.example.learningassistant.data.database.REF_STORAGE_ROOT
import com.example.learningassistant.data.database.UID
import com.example.learningassistant.data.database.intefaces.DatabaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class AppFirebaseRepository @Inject constructor() : DatabaseRepository {

//    init {
//        AUTH = FirebaseAuth.getInstance()
//    }


    override fun connectToDatabase(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit
    ) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
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
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                initRefs()
                onSuccess()
            }
            .addOnFailureListener {
                onFail(it.message.toString())
            }
    }


     fun initRefs() {
         UID = FirebaseAuth.getInstance().currentUser?.uid.toString()
         REF_STORAGE_ROOT = FirebaseStorage.getInstance().reference
        DB = FirebaseFirestore.getInstance()
    }

    override fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }
}