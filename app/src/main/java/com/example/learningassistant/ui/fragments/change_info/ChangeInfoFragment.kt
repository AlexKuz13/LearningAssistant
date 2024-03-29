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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        mViewModel = ViewModelProvider(this).get(ChangeInfoFragmentViewModel::class.java)
        return mBinding.root
    }


    override fun change() {
        super.change()
        val info = mBinding.etChangeInfo.text.toString()
        if (info != USER.info) {
            mViewModel.changeInfo(info) {
                showToast(resources.getString(R.string.data_update))
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