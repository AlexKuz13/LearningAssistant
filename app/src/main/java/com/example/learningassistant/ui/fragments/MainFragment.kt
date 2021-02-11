package com.example.learningassistant.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.learningassistant.R
import com.example.learningassistant.database.USER
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.showToast


class MainFragment : Fragment(R.layout.fragment_main) {
    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title= "Learning Assistant"
        APP_ACTIVITY.mNavDrawer.enableDrawer()
    }
}