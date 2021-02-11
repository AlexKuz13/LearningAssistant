package com.example.learningassistant.ui.fragments.settings

import android.view.*
import com.example.learningassistant.R
import com.example.learningassistant.database.USER
import com.example.learningassistant.ui.fragments.BaseFragment
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.replaceFragment
import com.example.learningassistant.utilits.showToast
import kotlinx.android.synthetic.main.fragment_settings.*


class SettingsFragment : BaseFragment(R.layout.fragment_settings) {
    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title="Настройки"
        setHasOptionsMenu(true)
        initFields()
    }

    private fun initFields() {
        settings_phone_number.text=USER.phone
        settings_header_fullname.text=USER.fullName
        settings_info.text=USER.info
        settings_rating.text=USER.rating.toString()
        settings_number_work.text=USER.completeWorks.toString()
        settings_header_status.text=USER.status
        settings_layout_info.setOnClickListener { replaceFragment(ChangeInfoFragment()) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.settings_action_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settings_menu_change_name -> replaceFragment(ChangeNameFragment())
        }
        return true
    }
}