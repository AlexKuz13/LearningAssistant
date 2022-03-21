package com.example.learningassistant.ui.adapters.binding

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.example.learningassistant.models.User
import com.example.learningassistant.ui.fragments.chats.ChatsFragmentDirections
import com.example.learningassistant.utilits.asTimeMessage
import com.google.android.material.card.MaterialCardView


@BindingAdapter("onChatClickListener")
fun onChatClickListener(layout: ConstraintLayout, user: User?) {
    if (user != null) {
        layout.setOnClickListener {
            try {
                val action =
                    ChatsFragmentDirections.actionChatsFragmentToMessagesFragment(user)
                layout.findNavController().navigate(action)
            } catch (e: Exception) {
                Log.i("onChatClick", e.toString())
            }
        }
    }
}

@BindingAdapter(value = ["visibilityMessage", "visibilityImage"], requireAll = true)

fun visibilityMessage(card: MaterialCardView, user: Boolean, msgTxt: Boolean) {
    if (user && msgTxt) card.visibility = View.VISIBLE
    else card.visibility = View.GONE
}

@BindingAdapter("setTimeMessage")
fun setTimeMessage(textView: TextView, time: String?) {
    if (time != null)
        textView.text = time.asTimeMessage()
}

