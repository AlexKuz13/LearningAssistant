package com.example.learningassistant.ui.fragments.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.learningassistant.R
import com.example.learningassistant.database.*
import com.example.learningassistant.databinding.FragmentEnterBinding
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.restartActivity
import com.example.learningassistant.utilits.showToast


class EnterFragment : Fragment() {

    private var _binding: FragmentEnterBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnterBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }


    override fun onResume() {
        super.onResume()
        mBinding.tvRegister.setOnClickListener { APP_ACTIVITY.navController.navigate(R.id.action_enterFragment_to_registerFragment) }
        mBinding.btnLogin.setOnClickListener { login() }
    }

    private fun login() {
        if (mBinding.loginInputEmail.text.isEmpty() || mBinding.loginInputPassword.text.isEmpty()) {
            showToast("Заполните поля!")
        } else {
            val email = mBinding.loginInputEmail.text.toString()
            val password = mBinding.loginInputPassword.text.toString()
            AUTH.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                showToast("Добро пожаловать")
                restartActivity()
            }
                .addOnFailureListener { showToast(it.message.toString()) }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}