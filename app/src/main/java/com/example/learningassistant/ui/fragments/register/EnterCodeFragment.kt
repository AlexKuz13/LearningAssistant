package com.example.learningassistant.ui.fragments.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.learningassistant.R
import com.example.learningassistant.database.*
import com.example.learningassistant.databinding.FragmentEnterCodeBinding
import com.example.learningassistant.utilits.*
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_enter_code.*

class EnterCodeFragment :
    Fragment(R.layout.fragment_enter_code) {

    private var _binding: FragmentEnterCodeBinding? = null
    private val mBinding get() = _binding!!
    lateinit var phoneNumber: String
    lateinit var id: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnterCodeBinding.inflate(layoutInflater, container, false)
        phoneNumber = arguments?.getString("phoneNumber").toString()
        id = arguments?.getString("id").toString()
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        mBinding.registerEnterCode.addTextChangedListener(AppTextWatcher {
            val string = register_enter_code.text.toString()
            if (string.length == 6) {
                enterCode()
            }
        })
    }

    private fun enterCode() {
        val code = mBinding.registerEnterCode.text.toString()
        val credential = PhoneAuthProvider.getCredential(id, code)
        AUTH.signInWithCredential(credential)
            .addOnSuccessListener {
                val uid = AUTH.currentUser?.uid.toString()
                DB.collection(COLL_USERS).document(uid)
                    .get().addOnCompleteListener {
                        if (it.result?.exists() == true) {
                            showToast("С возвращением!")
                            APP_ACTIVITY.hideKeyboard()
                            //APP_ACTIVITY.navController.navigate(R.id.action_enterCodeFragment_to_mainFragment)
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
                // APP_ACTIVITY.navController.navigate(R.id.action_enterCodeFragment_to_mainFragment)
                restartActivity()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}