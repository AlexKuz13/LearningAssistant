package com.example.learningassistant

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.learningassistant.database.AUTH
import com.example.learningassistant.database.initFirebase
import com.example.learningassistant.database.initUser
import com.example.learningassistant.databinding.ActivityMainBinding
import com.example.learningassistant.ui.objects.NavDrawer
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.AppStates
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mToolbar: androidx.appcompat.widget.Toolbar
    lateinit var navController:NavController
    lateinit var mNavDrawer: NavDrawer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        APP_ACTIVITY = this
        navController= Navigation.findNavController(this,R.id.nav_host_fragment)

        //initFirebase()
        //initUser {
        //    initFields()
        //    initFunc()
       // }
    }


    private fun initFunc() {
        setSupportActionBar(mToolbar)
        if (AUTH.currentUser != null) {
            mNavDrawer.create()
            APP_ACTIVITY.mNavDrawer.updateHeader()
            navController.navigate(R.id.action_enterFragment_to_mainFragment)
        } else {
            mToolbar.visibility = View.GONE
            mBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }
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

    override fun onStop() {
        super.onStop()
     //   AppStates.updateState(AppStates.OFFLINE)
    }

    override fun onResume() {
        super.onResume()
     //   android.os.Handler().postDelayed({
    //        AppStates.updateState((AppStates.ONLINE))
     //   }, 3000)
    }
}