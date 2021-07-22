package com.example.learningassistant.ui.fragments.change_name

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import androidx.lifecycle.ViewModelProvider
import com.example.learningassistant.R
import com.example.learningassistant.database.USER
import com.example.learningassistant.databinding.FragmentChangeNameBinding
import com.example.learningassistant.ui.fragments.settings.BaseChangeFragment
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.showToast


class ChangeNameFragment : BaseChangeFragment() {

    private var _binding: FragmentChangeNameBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mViewModel: ChangeNameFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_change_name, container, false)
        mBinding.user = USER
        return mBinding.root
    }


    override fun change() {
        val name = mBinding.etChangeName.text.toString()
        val surname = mBinding.etChangeSurname.text.toString()
        if (name.isEmpty() || surname.isEmpty()) {
            showToast("Поля не могут быть пустыми")
        } else {
            val fullName = "$name $surname"
            if (fullName != USER.fullName) {
                mViewModel = ViewModelProvider(this, ChangeNameViewModelFactory(fullName)).get(
                    ChangeNameFragmentViewModel::class.java
                )
                mViewModel.changeName {
                    showToast("Данные обновлены")
                    APP_ACTIVITY.mNavDrawer.updateHeader()
                    APP_ACTIVITY.navController.popBackStack()
                }
            } else APP_ACTIVITY.navController.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
