package com.example.learningassistant.ui.fragments.messages

import com.example.learningassistant.R
import com.example.learningassistant.ui.fragments.BaseFragment
import com.example.learningassistant.utilits.APP_ACTIVITY

class MessagesFragment : BaseFragment(R.layout.fragment_messages) {
    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title="Сообщения"
    }
}