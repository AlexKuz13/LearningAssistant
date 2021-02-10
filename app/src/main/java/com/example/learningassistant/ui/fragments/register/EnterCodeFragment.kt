package com.example.learningassistant.ui.fragments.register

import androidx.fragment.app.Fragment
import com.example.learningassistant.R
import com.example.learningassistant.database.*
import com.example.learningassistant.utilits.*
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_enter_code.*

class EnterCodeFragment(val phoneNumber: String, val id: String) : Fragment(R.layout.fragment_enter_code) {


    override fun onStart() {
        super.onStart()
        register_enter_code.addTextChangedListener(AppTextWatcher{
            val string=register_enter_code.text.toString()
            if(string.length==6){
                enterCode()
            }
        })
    }

    private fun enterCode() {
        val code = register_enter_code.text.toString()
        val credential = PhoneAuthProvider.getCredential(id,code)
        AUTH.signInWithCredential(credential)
            .addOnSuccessListener {
                val uid= AUTH.currentUser?.uid.toString()
                USER.id=uid
                USER.phone=phoneNumber
                DB.collection(COLL_USERS).document(uid).set(USER)
                    .addOnSuccessListener {
                        showToast("Добро пожаловать")
                        APP_ACTIVITY.hideKeyboard()
                        restartActivity()
                    }
                    .addOnFailureListener { showToast(it.message.toString()) }
            }
            .addOnFailureListener { showToast(it.message.toString()) }
    }
}