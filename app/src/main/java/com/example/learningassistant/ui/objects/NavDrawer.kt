package com.example.learningassistant.ui.objects

import android.service.dreams.DreamService
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.learningassistant.R
import com.example.learningassistant.ui.fragments.MessagesFragment
import com.example.learningassistant.ui.fragments.SettingsFragment
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.replaceFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class NavDrawer(private var toolbar: Toolbar) {
    lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mNavigationView: NavigationView
    private lateinit var mToggle: ActionBarDrawerToggle

    fun create() {
        initView()
        initNavDrawer()
    }

    private fun initView() {
         mDrawerLayout = APP_ACTIVITY.drawer_layout
        mNavigationView = APP_ACTIVITY.nav_view
    }

    private fun initNavDrawer() {
        mToggle = ActionBarDrawerToggle(
            APP_ACTIVITY, mDrawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        mDrawerLayout.addDrawerListener(mToggle)
        mToggle.syncState()
        mNavigationView.bringToFront()
        mNavigationView.setNavigationItemSelectedListener(APP_ACTIVITY)
    }

    inline fun onBackPressedExt(crossinline function: () -> Unit) {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START)
        } else function()
    }

    fun onNavMenuSelected(item: MenuItem) {
        when(item.itemId){
            R.id.nav_settings -> replaceFragment(SettingsFragment())
            R.id.nav_messages -> replaceFragment(MessagesFragment())
        }
        mDrawerLayout.closeDrawer(GravityCompat.START)
    }

    fun disableDrawer(){
       toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        toolbar.setNavigationOnClickListener {
            APP_ACTIVITY.supportFragmentManager.popBackStack()
        }
    }

    fun enableDrawer(){
        toolbar.setNavigationIcon(R.drawable.ic_nav_menu)
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        toolbar.setNavigationOnClickListener {
           mDrawerLayout.openDrawer(GravityCompat.START)
        }
    }

}