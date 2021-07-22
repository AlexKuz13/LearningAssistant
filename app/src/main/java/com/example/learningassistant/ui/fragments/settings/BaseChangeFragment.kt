package com.example.learningassistant.ui.fragments.settings

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.example.learningassistant.R
import com.example.learningassistant.ui.fragments.BaseFragment
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.hideKeyboard

open class BaseChangeFragment : BaseFragment() {


    override fun onStart() {
        super.onStart()
        setHasOptionsMenu(true)
    }

    override fun onStop() {
        super.onStop()
        APP_ACTIVITY.hideKeyboard()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.settings_menu_confirm,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settings_confirm_change -> change()
        }
        return true
    }

    open fun change() {

    }
}