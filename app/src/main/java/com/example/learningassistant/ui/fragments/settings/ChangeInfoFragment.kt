package com.example.learningassistant.ui.fragments.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.learningassistant.R
import com.example.learningassistant.database.*
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.showToast
import kotlinx.android.synthetic.main.fragment_change_info.*


class ChangeInfoFragment : BaseChangeFragment(R.layout.fragment_change_info) {
    override fun onResume() {
        super.onResume()
        et_change_info.setText(USER.info)
    }

    override fun change() {
        super.change()
        val info= et_change_info.text.toString()
        DB.collection(COLL_USERS).document(UID).update(CHIlD_INFO,info)
            .addOnSuccessListener {
                showToast("Данные обновлены")
                USER.info = info
                fragmentManager?.popBackStack()
            }
            .addOnFailureListener { showToast(it.message.toString()) }
    }
}