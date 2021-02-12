package com.example.learningassistant.utilits

import android.content.Context
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.learningassistant.MainActivity
import com.example.learningassistant.R
import com.squareup.picasso.Picasso

fun showToast(message:String){
    Toast.makeText(APP_ACTIVITY,message, Toast.LENGTH_SHORT).show()
}

fun replaceFragment(fragment: Fragment, addStack:Boolean=true){
    if(addStack) {
        APP_ACTIVITY.supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.data_container, fragment).commit()
    } else {
        APP_ACTIVITY.supportFragmentManager.beginTransaction()
            .replace(R.id.data_container, fragment).commit()
    }
}

fun restartActivity() {
    val intent = Intent(APP_ACTIVITY, MainActivity::class.java)
    APP_ACTIVITY.startActivity(intent)
    APP_ACTIVITY.finish()
}

fun AppCompatActivity.hideKeyboard(){
    val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(window.decorView.windowToken,0)
}

fun ImageView.downloadAndSetImage(url:String){
    Picasso.get()
        .load(url)
        .fit()
        .placeholder(R.drawable.ic_default_profile_photo)
        .into(this)
}