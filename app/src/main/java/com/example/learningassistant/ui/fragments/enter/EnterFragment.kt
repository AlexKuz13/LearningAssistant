package com.example.learningassistant.ui.fragments.enter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.learningassistant.R
import com.example.learningassistant.databinding.FragmentEnterBinding
import com.example.learningassistant.ui.objects.AppPreference
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.showToast
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        mViewModel = ViewModelProvider(this).get(EnterFragmentViewModel::class.java)
        return mBinding.root
    }


    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        APP_ACTIVITY.mToolbar.visibility = View.GONE
        mBinding.tvRegister.setOnClickListener { APP_ACTIVITY.navController.navigate(R.id.action_enterFragment_to_registerFragment) }
        mBinding.btnLogin.setOnClickListener { login() }
        mBinding.btnEnterVk.setOnClickListener {
            VK.login(APP_ACTIVITY, arrayListOf(VKScope.WALL, VKScope.PHOTOS))
        }
    }

    private fun login() {
        if (mBinding.loginInputEmail.text.isEmpty() || mBinding.loginInputPassword.text.isEmpty()) {
            showToast(resources.getString(R.string.fill_fields))
        } else {
            val email = mBinding.loginInputEmail.text.toString()
            val password = mBinding.loginInputPassword.text.toString()
            mViewModel.initDatabase(email, password) {
                AppPreference.setInitUser(true)
                APP_ACTIVITY.navController.navigate(R.id.action_enterFragment_to_mainFragment)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                APP_ACTIVITY.navController.navigate(R.id.action_enterFragment_to_mainFragment)
            }

            override fun onLoginFailed(errorCode: Int) {
                showToast("Ошибка авторизации")
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}