package com.example.learningassistant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.learningassistant.databinding.ActivityMainBinding
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mDrawerLayout:DrawerLayout
    private lateinit var mNavigationView:NavigationView
    private lateinit var mToolbar:androidx.appcompat.widget.Toolbar
    private lateinit var mToggle: ActionBarDrawerToggle

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
        mToggle= ActionBarDrawerToggle(APP_ACTIVITY,mDrawerLayout,mToolbar,
            R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        mDrawerLayout.addDrawerListener(mToggle)
        mToggle.syncState()



            //проверка на авторизацию и замена фрагментов
    }


    private fun initFields() {
        mDrawerLayout = mBinding.drawerLayout
        mNavigationView = mBinding.navView
        mToolbar = mBinding.mainToolbar
    }
}