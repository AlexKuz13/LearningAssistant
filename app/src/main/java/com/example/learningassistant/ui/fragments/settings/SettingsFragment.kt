package com.example.learningassistant.ui.fragments.settings

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.learningassistant.R
import com.example.learningassistant.database.*
import com.example.learningassistant.databinding.FragmentSettingsBinding
import com.example.learningassistant.models.User
import com.example.learningassistant.ui.fragments.BaseFragment
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.downloadAndSetImage
import com.example.learningassistant.utilits.showToast
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView


class SettingsFragment : BaseFragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mViewModel: SettingsFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_settings, container, false)
        mBinding.user = arguments?.getSerializable("User") as User
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        if (mBinding.user != USER) {
            APP_ACTIVITY.title = "Пользователь"
            mBinding.userBoolean = false
        } else {
            APP_ACTIVITY.title = "Настройки"
            setHasOptionsMenu(true)
            mBinding.userBoolean = true
            mBinding.settingsChangePhoto.setOnClickListener { changePhotoUser() }
            mBinding.settingsLayoutInfo.setOnClickListener {
                APP_ACTIVITY.navController.navigate(R.id.action_settingsFragment_to_changeInfoFragment)
            }
        }
    }


    private fun changePhotoUser() {
        CropImage.activity()
            .setAspectRatio(1, 1)
            .setRequestedSize(600, 600)
            .setCropShape(CropImageView.CropShape.OVAL)
            .start(APP_ACTIVITY, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE &&
            resultCode == Activity.RESULT_OK && data != null
        ) {
            val uri = CropImage.getActivityResult(data).uri
            val path = REF_STORAGE_ROOT.child(FOLDER_PROFILE_IMAGE)
                .child(UID)
            mViewModel = ViewModelProvider(this,SettingsViewModelFactory(uri,path)).get(
                SettingsFragmentViewModel::class.java)
            mViewModel.updatePhoto {
                mBinding.settingsHeaderProfilePhoto.downloadAndSetImage(USER.photoUrl)
                showToast("Данные обновлены")
                APP_ACTIVITY.mNavDrawer.updateHeader()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.settings_action_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu_change_name -> APP_ACTIVITY.navController.navigate(R.id.action_settingsFragment_to_changeNameFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}