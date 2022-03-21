package com.example.learningassistant.ui.adapters.binding

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.example.learningassistant.R
import com.example.learningassistant.models.User
import com.example.learningassistant.ui.fragments.main.MainFragmentDirections
import com.example.learningassistant.ui.fragments.messages.MessagesFragmentDirections
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.asTime
import com.squareup.picasso.Picasso


@BindingAdapter("loadImageFromUrl")
fun loadImageFromUrl(imageView: ImageView, imageUrl: String?) {
    if (imageUrl != null) {
        Picasso.get()
            .load(imageUrl)
            .fit()
            .placeholder(R.drawable.ic_default_profile_photo)
            .into(imageView)
    }
}


@BindingAdapter("setTimeTask")
fun setTimeTask(textView: TextView, time: String?) {
    if (time != null)
        textView.text = time.asTime()
}

@BindingAdapter("visibilityButton")
fun visibilityButton(button: Button, user: Boolean) {
    if (user) button.visibility = View.VISIBLE
    else button.visibility = View.INVISIBLE
}

@BindingAdapter("onImageClickListener", "fromFragment", requireAll = true)
fun onImageClickListener(imageView: ImageView, user: User?, fragment: String?) {
    if (user != null && fragment != null) {
        imageView.setOnClickListener {
            try {
                when (fragment) {
                    "main" -> {
                        val action =
                            MainFragmentDirections.actionMainFragmentToSettingsFragment(user)
                        imageView.findNavController().navigate(action)
                    }
                    "messages" -> {
                        val action =
                            MessagesFragmentDirections.actionMessagesFragmentToSettingsFragment(
                                user
                            )
                        APP_ACTIVITY.navController.navigate(action)
                    }
                }
            } catch (e: Exception) {
                Log.i("onImageClick", e.toString())
            }
        }
    }
}

@BindingAdapter("onBtnHelpClickListener")
fun onBtnHelpClickListener(button: Button, user: User?) {
    if (user != null) {
        button.setOnClickListener {
            try {
                val action =
                    MainFragmentDirections.actionMainFragmentToMessagesFragment(user)
                button.findNavController().navigate(action)
            } catch (e: Exception) {
                Log.i("onBtnHelpClick", e.toString())
            }
        }
    }

}

