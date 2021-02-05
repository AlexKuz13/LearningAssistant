package com.example.learningassistant

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.learningassistant.databinding.ActivityMainBinding
import com.example.learningassistant.ui.fragments.MainFragment
import com.example.learningassistant.ui.objects.NavDrawer
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.replaceFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mToolbar: androidx.appcompat.widget.Toolbar
    lateinit var mNavDrawer: NavDrawer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        APP_ACTIVITY = this
        initFields()
        initFunc()


    }

    private fun initFunc() {
        setSupportActionBar(mToolbar)
        mNavDrawer.create()
        replaceFragment(MainFragment(), false)

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