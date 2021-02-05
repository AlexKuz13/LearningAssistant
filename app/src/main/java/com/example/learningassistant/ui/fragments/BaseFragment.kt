package com.example.learningassistant.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.learningassistant.MainActivity
import com.example.learningassistant.R
import com.example.learningassistant.utilits.APP_ACTIVITY


open class BaseFragment(layout:Int) : Fragment(layout) {
    override fun onStart() {
        super.onStart()
        APP_ACTIVITY.mNavDrawer.disableDrawer()
    }

}