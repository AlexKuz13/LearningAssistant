package com.example.learningassistant.utilits

import android.content.Context
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import com.example.learningassistant.MainActivity
import com.example.learningassistant.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*

fun showToast(message: String) {
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_SHORT).show()
}

fun restartActivity() {
    val intent = Intent(APP_ACTIVITY, MainActivity::class.java)
    APP_ACTIVITY.startActivity(intent)
    APP_ACTIVITY.finish()
}

fun AppCompatActivity.hideKeyboard() {
    val imm: InputMethodManager =
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
}

@BindingAdapter("android:src")
fun ImageView.downloadAndSetImage(url: String) {
    Picasso.get()
        .load(url)
        .fit()
        .placeholder(R.drawable.ic_default_profile_photo)
        .into(this)
}

fun String.asTime(): String {
    val seconds = this.substringBefore(',').substringAfter('=') + "000"
    val time = Date(seconds.toLong())
    val timeFormat = SimpleDateFormat("dd.MM HH:mm", Locale.getDefault())
    return timeFormat.format(time)
}

fun String.asTimeMessage(): String {
    val seconds = this.substringBefore(',').substringAfter('=') + "000"
    val time = Date(seconds.toLong())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return timeFormat.format(time)
}