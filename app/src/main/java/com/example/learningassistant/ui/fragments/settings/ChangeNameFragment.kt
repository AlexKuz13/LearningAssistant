package com.example.learningassistant.ui.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.learningassistant.database.*
import com.example.learningassistant.databinding.FragmentChangeNameBinding
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.showToast


class ChangeNameFragment : BaseChangeFragment() {

    private var _binding: FragmentChangeNameBinding? = null
    private val mBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangeNameBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        val fullnameList = USER.fullName.split(" ")
        if (fullnameList.size > 1) {
            mBinding.etChangeName.setText(fullnameList[0])
            mBinding.etChangeSurname.setText(fullnameList[1])
        } else mBinding.etChangeName.setText(fullnameList[0])
    }

    override fun change() {
        val name = mBinding.etChangeName.text.toString()
        val surname = mBinding.etChangeSurname.text.toString()
        if (name.isEmpty()) {
            showToast("Имя не может быть пустым")
        } else {
            val fullname = "$name $surname"
            DB.collection(COLL_USERS).document(UID).update(CHILD_FULLNAME, fullname)
                .addOnSuccessListener {
                    showToast("Данные обновлены")
                    USER.fullName = fullname
                    APP_ACTIVITY.mNavDrawer.updateHeader()
                    APP_ACTIVITY.navController.popBackStack()
                }
                .addOnFailureListener { showToast(it.message.toString()) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
