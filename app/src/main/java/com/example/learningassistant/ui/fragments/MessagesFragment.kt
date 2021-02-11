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

class MessagesFragment : BaseFragment(R.layout.fragment_messages) {
    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title="Сообщения"
    }
}