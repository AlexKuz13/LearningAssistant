package com.example.learningassistant.ui.fragments.register

import androidx.fragment.app.Fragment
import com.example.learningassistant.R
import com.example.learningassistant.database.*
import com.example.learningassistant.utilits.*
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_register.*
import java.util.concurrent.TimeUnit


class RegisterFragment : Fragment(R.layout.fragment_register) {


    private lateinit var mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var mPhoneNumber: String

    override fun onStart() {
        super.onStart()
        mCallBack = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationFailed(p0: FirebaseException) {
                showToast(p0.message.toString())
            }

            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                replaceFragment(EnterCodeFragment(mPhoneNumber, id))
            }

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                AUTH.signInWithCredential(credential)
                    .addOnSuccessListener {
                        val uid = AUTH.currentUser?.uid.toString()
                        DB.collection(COLL_USERS).document(uid).get()
                            .addOnCompleteListener {
                                if (it.result?.exists() != true) {
                                    USER.id = uid
                                    USER.phone = mPhoneNumber
                                    DB.collection(COLL_USERS).document(uid).set(USER).addOnSuccessListener {
                                            val map = hashMapOf("rating_sum" to 0.0)
                                            DB.collection(COLL_RATINGS).document(uid).set(map)
                                                .addOnFailureListener { showToast(it.message.toString()) }
                                            showToast("Добро пожаловать")
                                            APP_ACTIVITY.hideKeyboard()
                                            restartActivity()
                                        }
                                } else {
                                    showToast("C возвращением!")
                                    restartActivity()
                                }
                            }
                            .addOnFailureListener { showToast(it.message.toString()) }
                    }
            }


    }

    register_btn_next.setOnClickListener{
        sentCode()
    }
}

private fun sentCode() {
    if (register_input_phone_number.text.toString().isEmpty()) {
        showToast("Введите номер телефона")
    } else
        authUser()
}

private fun authUser() {
    mPhoneNumber = register_input_phone_number.text.toString()
    PhoneAuthProvider.verifyPhoneNumber(
        PhoneAuthOptions
            .newBuilder(FirebaseAuth.getInstance())
            .setActivity(APP_ACTIVITY)
            .setPhoneNumber(mPhoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setCallbacks(mCallBack)
            .build()
    )
}
}