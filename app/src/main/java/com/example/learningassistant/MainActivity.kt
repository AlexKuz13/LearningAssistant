package com.example.learningassistant

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.learningassistant.database.AUTH
import com.example.learningassistant.database.initFirebase
import com.example.learningassistant.database.initUser
import com.example.learningassistant.databinding.ActivityMainBinding
import com.example.learningassistant.ui.fragments.MainFragment
import com.example.learningassistant.ui.fragments.register.RegisterFragment
import com.example.learningassistant.ui.objects.NavDrawer
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.replaceFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mToolbar: androidx.appcompat.widget.Toolbar
    lateinit var mNavDrawer: NavDrawer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        APP_ACTIVITY = this
        initFirebase()
        initUser()
        initFields()
        initFunc()


    }

    private fun initFunc() {
        setSupportActionBar(mToolbar)
        if (AUTH.currentUser!=null) {
            mNavDrawer.create()
            replaceFragment(MainFragment(), false)
        } else {
            mToolbar.visibility = View.GONE
            drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            replaceFragment(RegisterFragment(), false)
        }

        //проверка на авторизацию и замена фрагментов
    }


    private fun initFields() {
        mToolbar = mBinding.mainToolbar
        mNavDrawer = NavDrawer(mToolbar)

    }

    override fun onBackPressed() {
        mNavDrawer.onBackPressedExt() {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        mNavDrawer.onNavMenuSelected(item)
        return true
    }
}