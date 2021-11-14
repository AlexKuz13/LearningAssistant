package com.example.learningassistant.ui.adapters.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter


class HeaderMenuBinding {

    companion object {
        @BindingAdapter("night", "drawableNight", "drawableDay")
        @JvmStatic
        fun onNightModeClickListener(
            imageView: ImageView,
            night: Boolean,
            drawableNight: Drawable,
            drawableDay: Drawable
        ) {
            if (night)
                imageView.setImageDrawable(drawableNight)
            else imageView.setImageDrawable(drawableDay)

        }
    }
}