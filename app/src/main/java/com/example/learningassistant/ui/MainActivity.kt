package com.example.learningassistant.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.learningassistant.R
import com.example.learningassistant.data.database.firebase.AppFirebaseRepository
import com.example.learningassistant.databinding.ActivityMainBinding
import com.example.learningassistant.ui.fragments.settings.SettingsFragmentViewModel
import com.example.learningassistant.ui.objects.AppPreference
import com.example.learningassistant.ui.objects.NavDrawer
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.observeOnce
import com.example.learningassistant.utilits.setLocal
import com.google.android.material.navigation.NavigationView
import com.vk.api.sdk.utils.VKUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var _binding: ActivityMainBinding? = null
    val mBinding get() = _binding!!
    lateinit var mToolbar: androidx.appcompat.widget.Toolbar
    lateinit var navController: NavController
    lateinit var mNavDrawer: NavDrawer
    lateinit var mDrawerLayout: DrawerLayout

    private lateinit var settingsViewModel: SettingsFragmentViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_LearningAssistant)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        initLanguage()

        setContentView(mBinding.root)

        APP_ACTIVITY = this
        val finger = VKUtils.getCertificateFingerprint(this, this.packageName)
        finger?.forEach {
            if (it != null) {
                Log.d("dfdfd", it)
            }
        }
        AppPreference.getPreference(this)
        initFields()
        mNavDrawer.create()

    }

    private fun initLanguage() {
        settingsViewModel = ViewModelProvider(this).get(SettingsFragmentViewModel::class.java)
        settingsViewModel.readLangCodeAndId.asLiveData().observeOnce(this, {
            setLocal(it.langCode, this)
        })
    }


    private fun initFields() {
        mToolbar = mBinding.mainToolbar
        setSupportActionBar(mToolbar)
        mNavDrawer = NavDrawer(mToolbar)
        mDrawerLayout = mBinding.drawerLayout

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        if (AppPreference.getInitUser()) {
            AppFirebaseRepository().initRefs()
            navController.navigate(R.id.action_enterFragment_to_mainFragment)
        }
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

    override fun onDestroy() {
        super.onDestroy()
    }
}