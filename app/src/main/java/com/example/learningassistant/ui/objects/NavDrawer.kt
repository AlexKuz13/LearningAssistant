package com.example.learningassistant.ui.objects

import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.learningassistant.R
import com.example.learningassistant.data.database.USER
import com.example.learningassistant.databinding.HeaderBinding
import com.example.learningassistant.ui.fragments.main.MainFragmentDirections
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.AppStates
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth


class NavDrawer(private var toolbar: Toolbar) {
    lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mNavigationView: NavigationView
    private lateinit var mToggle: ActionBarDrawerToggle
    lateinit var headerBinding: HeaderBinding

    fun create() {
        initView()
        initNavDrawer()
    }

    fun updateHeader() {
        headerBinding.user = USER
    }

    private fun initView() {
        mDrawerLayout = APP_ACTIVITY.mDrawerLayout
        mNavigationView = APP_ACTIVITY.mBinding.navView
        headerBinding = HeaderBinding.bind(mNavigationView.getHeaderView(0))
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
        when (item.itemId) {
            R.id.nav_settings -> {
                val action = MainFragmentDirections.actionMainFragmentToSettingsFragment(USER)
                APP_ACTIVITY.navController.navigate(action)
            }
            R.id.nav_my_tasks -> APP_ACTIVITY.navController.navigate(R.id.action_mainFragment_to_myTasksFragment)
            R.id.nav_messages -> APP_ACTIVITY.navController.navigate(R.id.action_mainFragment_to_chatsFragment)
            R.id.nav_logout -> {
                AppStates.updateState(AppStates.OFFLINE)
                FirebaseAuth.getInstance().signOut() // заменить возможно на appFirebaseRepository
                AppPreference.setInitUser(false)
                APP_ACTIVITY.navController.navigate(R.id.action_mainFragment_to_enterFragment)
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START)
    }

    fun disableDrawer() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        toolbar.setNavigationOnClickListener {
            APP_ACTIVITY.navController.popBackStack()
        }
    }

    fun enableDrawer() {
        toolbar.setNavigationIcon(R.drawable.ic_nav_menu)
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        toolbar.setNavigationOnClickListener {
            mDrawerLayout.openDrawer(GravityCompat.START)
        }
        mNavigationView.menu.clear()
        mNavigationView.inflateMenu(R.menu.nav_menu)
    }
}