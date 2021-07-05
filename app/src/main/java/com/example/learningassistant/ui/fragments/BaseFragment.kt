package com.example.learningassistant.ui.fragments

import androidx.fragment.app.Fragment
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.hideKeyboard


open class BaseFragment : Fragment() {
    override fun onStart() {
        super.onStart()
        APP_ACTIVITY.mNavDrawer.disableDrawer()
    }

    override fun onStop() {
        super.onStop()
        APP_ACTIVITY.hideKeyboard()
    }

}