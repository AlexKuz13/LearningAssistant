package com.example.learningassistant.ui.fragments.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.learningassistant.R
import com.example.learningassistant.database.*
import com.example.learningassistant.databinding.FragmentRegisterBinding
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.hideKeyboard
import com.example.learningassistant.utilits.showToast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var mPhoneNumber: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        mCallBack = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationFailed(p0: FirebaseException) {
                showToast(p0.message.toString())
            }

            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                val bundle = Bundle()
                bundle.putString("phoneNumber", mPhoneNumber)
                bundle.putString("id", id)
                APP_ACTIVITY.navController.navigate(
                    R.id.action_registerFragment_to_enterCodeFragment,
                    bundle
                )
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
                                    DB.collection(COLL_USERS).document(uid).set(USER)
                                        .addOnSuccessListener {
                                            val map = hashMapOf("rating_sum" to 0.0)
                                            DB.collection(COLL_RATINGS).document(uid).set(map)
                                                .addOnFailureListener { showToast(it.message.toString()) }
                                            showToast("Добро пожаловать")
                                            APP_ACTIVITY.hideKeyboard()
                                            APP_ACTIVITY.navController.navigate(R.id.action_registerFragment_to_mainFragment)
                                        }
                                } else {
                                    showToast("C возвращением!")
                                    APP_ACTIVITY.navController.navigate(R.id.action_registerFragment_to_mainFragment)
                                }
                            }
                            .addOnFailureListener { showToast(it.message.toString()) }
                    }
            }


        }

        mBinding.registerBtnNext.setOnClickListener {
            sentCode()
        }
    }

    private fun sentCode() {
        if (mBinding.registerInputPhoneNumber.text.toString().isEmpty()) {
            showToast("Введите номер телефона")
        } else
            authUser()
    }

    private fun authUser() {
        mPhoneNumber = mBinding.registerInputPhoneNumber.text.toString()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}