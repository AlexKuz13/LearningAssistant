package com.example.learningassistant.ui.fragments.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.learningassistant.R
import com.example.learningassistant.data.database.*
import com.example.learningassistant.databinding.FragmentRegisterBinding
import com.example.learningassistant.models.User
import com.example.learningassistant.ui.objects.AppPreference
import com.example.learningassistant.utilits.*
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mViewModel: RegisterFragmentViewModel


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
                lifecycleScope.launch {
                    readData()
                    mViewModel = ViewModelProvider(
                        this@RegisterFragment,
                        RegisterViewModelFactory(USER)
                    ).get(
                        RegisterFragmentViewModel::class.java
                    )
                    mViewModel.createDatabase {
                        showToast("Регистрация прошла успешно!")
                        AppPreference.setInitUser(true)
                        APP_ACTIVITY.navController.navigate(R.id.action_registerFragment_to_mainFragment)
                    }
                }
            }
        }
    }


    private fun checkFields(onSuccess: () -> Unit) {
        when {
            mBinding.registerInputName.text.isEmpty() -> showToast("Введите Имя")
            mBinding.registerInputSurname.text.isEmpty() -> showToast("Введите Фамилию")
            mBinding.registerInputEmail.text.isEmpty() -> showToast("Введите Email")
            mBinding.registerInputPassword.text.isEmpty() -> showToast("Введите Пароль")
            mBinding.registerRepeatPassword.text.isEmpty() -> showToast("Повторите Пароль")
            mBinding.registerInputPassword.text.toString() != mBinding.registerRepeatPassword.text.toString() ->
                showToast("Вы ввели разные пароли!")
            else -> onSuccess()
        }
    }

    private suspend fun readData() {
        val fullName =
            mBinding.registerInputName.text.toString() + " " + mBinding.registerInputSurname.text.toString()
        USER = User()
        USER.fullName = fullName
        USER.email = mBinding.registerInputEmail.text.toString()
        USER.password = mBinding.registerInputPassword.text.toString()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}