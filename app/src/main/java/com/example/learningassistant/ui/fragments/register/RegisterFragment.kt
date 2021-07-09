package com.example.learningassistant.ui.fragments.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.learningassistant.R
import com.example.learningassistant.database.*
import com.example.learningassistant.databinding.FragmentRegisterBinding
import com.example.learningassistant.utilits.*

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val mBinding get() = _binding!!


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
        mBinding.tvLogin.setOnClickListener { APP_ACTIVITY.navController.navigate(R.id.action_registerFragment_to_enterFragment) }

        mBinding.btnRegister.setOnClickListener {
            checkFields {
                readData {
                    register()
                }
            }
        }
    }


    private fun checkFields(function: () -> Unit) {
        when {
            mBinding.registerInputName.text.isEmpty() -> showToast("Введите Имя")
            mBinding.registerInputSurname.text.isEmpty() -> showToast("Введите Фамилию")
            mBinding.registerInputEmail.text.isEmpty() -> showToast("Введите Email")
            mBinding.registerInputPassword.text.isEmpty() -> showToast("Введите Пароль")
            mBinding.registerRepeatPassword.text.isEmpty() -> showToast("Повторите Пароль")
            mBinding.registerInputPassword.text.toString() != mBinding.registerRepeatPassword.text.toString() ->
                showToast("Вы ввели разные пароли!")
            else ->{
                function()
            }
        }
    }

    private fun readData(function: () -> Unit) {
        val fullName =
            mBinding.registerInputName.text.toString() + " " + mBinding.registerInputSurname.text.toString()
        USER.fullName = fullName
        USER.email = mBinding.registerInputEmail.text.toString()
        USER.password = mBinding.registerInputPassword.text.toString()
        function()
    }

    private fun register() {
        AUTH.createUserWithEmailAndPassword(USER.email, USER.password)
            .addOnSuccessListener {
                USER.id = AUTH.currentUser?.uid.toString()
                DB.collection(COLL_USERS).document(USER.id).set(USER)
                    .addOnSuccessListener {
                        val map = hashMapOf("rating_sum" to 0.0)
                        DB.collection(COLL_RATINGS).document(USER.id).set(map)
                            .addOnSuccessListener {
                                showToast("Регистрация прошла успешно!")
                                APP_ACTIVITY.hideKeyboard()
                                APP_ACTIVITY.navController.navigate(R.id.action_registerFragment_to_enterFragment)
                            }
                    }
            }
            .addOnFailureListener { showToast(it.message.toString()) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}