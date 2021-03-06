package com.example.learningassistant.ui.fragments.register

import androidx.fragment.app.Fragment
import com.example.learningassistant.R
import com.example.learningassistant.database.*
import com.example.learningassistant.utilits.*
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.fragment_enter_code.*

class EnterCodeFragment(private val phoneNumber: String, val id: String) :
    Fragment(R.layout.fragment_enter_code) {


    override fun onStart() {
        super.onStart()
        register_enter_code.addTextChangedListener(AppTextWatcher {
            val string = register_enter_code.text.toString()
            if (string.length == 6) {
                enterCode()
            }
        })
    }

    private fun enterCode() {
        val code = register_enter_code.text.toString()
        val credential = PhoneAuthProvider.getCredential(id, code)
        AUTH.signInWithCredential(credential)
            .addOnSuccessListener {
                val uid = AUTH.currentUser?.uid.toString()
                DB.collection(COLL_USERS).document(uid)
                    .get().addOnCompleteListener {
                        if (it.result?.exists() == true) {
                            showToast("С возвращением!")
                            APP_ACTIVITY.hideKeyboard()
                            restartActivity()
                        } else registerUser(uid)
                    }
             .addOnFailureListener { showToast(it.message.toString()) }
            }
    }

    private fun registerUser(uid: String) {
        USER.id = uid
        USER.phone = phoneNumber
        DB.collection(COLL_USERS).document(uid).set(USER)
            .addOnSuccessListener {
                val map = hashMapOf("rating_sum" to 0.0)
                DB.collection(COLL_RATINGS).document(uid).set(map)
                    .addOnFailureListener {
                        showToast(it.message.toString())
                    }
                showToast("Добро пожаловать")
                APP_ACTIVITY.hideKeyboard()
                restartActivity()
            }
    }
}