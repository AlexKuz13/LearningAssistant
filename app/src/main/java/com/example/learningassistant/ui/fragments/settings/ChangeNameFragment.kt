package com.example.learningassistant.ui.fragments.settings

import com.example.learningassistant.R
import com.example.learningassistant.database.*
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.showToast
import kotlinx.android.synthetic.main.fragment_change_name.*


class ChangeNameFragment : BaseChangeFragment(R.layout.fragment_change_name) {

    override fun onResume() {
        super.onResume()
        val fullnameList = USER.fullName.split(" ")
        if (fullnameList.size > 1) {
            et_change_name.setText(fullnameList[0])
            et_change_surname.setText(fullnameList[1])
        } else et_change_name.setText(fullnameList[0])
    }

    override fun change() {
        val name = et_change_name.text.toString()
        val surname = et_change_surname.text.toString()
        if (name.isEmpty()) {
            showToast("Имя не может быть пустым")
        } else {
            val fullname = "$name $surname"
            DB.collection(COLL_USERS).document(UID).update(CHILD_FULLNAME, fullname)
                .addOnSuccessListener {
                    showToast("Данные обновлены")
                    USER.fullName = fullname
                    APP_ACTIVITY.mNavDrawer.updateHeader()
                    fragmentManager?.popBackStack()
                }
                .addOnFailureListener { showToast(it.message.toString()) }
        }
    }
}
