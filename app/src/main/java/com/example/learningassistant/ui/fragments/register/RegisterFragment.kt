package com.example.learningassistant.ui.fragments.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import com.example.learningassistant.R
import com.example.learningassistant.ui.objects.NavDrawer
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.replaceFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment(R.layout.fragment_register) {



    override fun onStart() {
        super.onStart()
        register_btn_next.setOnClickListener {
            replaceFragment(EnterCodeFragment())
        }
    }
}