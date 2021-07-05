package com.example.learningassistant.ui.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.learningassistant.database.*
import com.example.learningassistant.databinding.FragmentChangeInfoBinding
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.showToast


class ChangeInfoFragment : BaseChangeFragment() {

    private var _binding: FragmentChangeInfoBinding? = null
    private val mBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangeInfoBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        mBinding.etChangeInfo.setText(USER.info)
    }

    override fun change() {
        super.change()
        val info = mBinding.etChangeInfo.text.toString()
        DB.collection(COLL_USERS).document(UID).update(CHIlD_INFO, info)
            .addOnSuccessListener {
                showToast("Данные обновлены")
                USER.info = info
                APP_ACTIVITY.navController.popBackStack()
            }
            .addOnFailureListener { showToast(it.message.toString()) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}