package com.example.learningassistant.ui.fragments.change_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.learningassistant.R
import com.example.learningassistant.data.database.USER
import com.example.learningassistant.databinding.FragmentChangeInfoBinding
import com.example.learningassistant.ui.fragments.settings.BaseChangeFragment
import com.example.learningassistant.utilits.showToast


class ChangeInfoFragment : BaseChangeFragment() {

    private var _binding: FragmentChangeInfoBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mViewModel: ChangeInfoFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_change_info, container, false)
        mBinding.user = USER
        return mBinding.root
    }


    override fun change() {
        super.change()
        val info = mBinding.etChangeInfo.text.toString()
        if (info != USER.info) {
            mViewModel = ViewModelProvider(this, ChangeInfoViewModelFactory(info)).get(
                ChangeInfoFragmentViewModel::class.java
            )
            mViewModel.changeInfo {
                showToast("Данные обновлены")
                USER.info = info
                findNavController().popBackStack()
            }
        } else findNavController().popBackStack()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}