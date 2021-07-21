package com.example.learningassistant.ui.fragments.enter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.learningassistant.MainActivity
import com.example.learningassistant.R
import com.example.learningassistant.databinding.FragmentEnterBinding
import com.example.learningassistant.ui.objects.AppPreference
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.showToast


class EnterFragment : Fragment() {

    private var _binding: FragmentEnterBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mViewModel: EnterFragmentViewModel

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
        APP_ACTIVITY.mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        APP_ACTIVITY.mToolbar.visibility = View.GONE
        mBinding.tvRegister.setOnClickListener { APP_ACTIVITY.navController.navigate(R.id.action_enterFragment_to_registerFragment) }
        mBinding.btnLogin.setOnClickListener { login() }
    }

    private fun login() {
        if (mBinding.loginInputEmail.text.isEmpty() || mBinding.loginInputPassword.text.isEmpty()) {
            showToast("Заполните поля!")
        } else {
            val email = mBinding.loginInputEmail.text.toString()
            val password = mBinding.loginInputPassword.text.toString()
            mViewModel = ViewModelProvider(this, EnterViewModelFactory(email, password)).get(
                EnterFragmentViewModel::class.java
            )
            mViewModel.initDatabase {
                showToast("Добро пожаловать")
                AppPreference.setInitUser(true)
                APP_ACTIVITY.navController.navigate(R.id.action_enterFragment_to_mainFragment)
            }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}