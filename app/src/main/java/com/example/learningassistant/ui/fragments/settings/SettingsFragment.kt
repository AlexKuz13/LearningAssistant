package com.example.learningassistant.ui.fragments.settings

import android.app.Activity
import android.content.Intent
import android.view.*
import com.example.learningassistant.R
import com.example.learningassistant.database.*
import com.example.learningassistant.models.User
import com.example.learningassistant.ui.fragments.BaseFragment
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.downloadAndSetImage
import com.example.learningassistant.utilits.replaceFragment
import com.example.learningassistant.utilits.showToast
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_settings.*
import java.text.DecimalFormat


class SettingsFragment(private val human: User) : BaseFragment(R.layout.fragment_settings) {


    override fun onResume() {
        super.onResume()
        if (human.id != UID) {
            APP_ACTIVITY.title = "Пользователь"
            initProfileUser()
        } else {
            APP_ACTIVITY.title = "Настройки"
            setHasOptionsMenu(true)
            initFields()
        }
    }

    private fun initProfileUser() {
        phone.text = getString(R.string.phone)
        info.text = getString(R.string.info)
        rating.text = getString(R.string.rating)
        settings_change_photo.visibility = View.GONE
        settings_phone_number.text = human.phone
        settings_header_fullname.text = human.fullName
        settings_info.text = human.info
        settings_rating.text = DecimalFormat("#.##").format(human.rating).toString()
        settings_number_work.text = human.completeWorks.toString()
        settings_header_status.text = human.status
        settings_header_profile_photo.downloadAndSetImage(human.photoUrl)
    }

    private fun initFields() {
        settings_phone_number.text = USER.phone
        settings_header_fullname.text = USER.fullName
        settings_info.text = USER.info
        settings_rating.text = USER.rating.toString()
        settings_number_work.text = USER.completeWorks.toString()
        settings_header_status.text = USER.status
        settings_header_profile_photo.downloadAndSetImage(USER.photoUrl)
        settings_layout_info.setOnClickListener { replaceFragment(ChangeInfoFragment()) }
        settings_change_photo.setOnClickListener { changePhotoUser() }
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
            putImageToStorage(uri, path) {
                getUrlFromStorage(path) {
                    putUrlToDatabase(it) {
                        settings_header_profile_photo.downloadAndSetImage(it)
                        showToast("Данные обновлены")
                        USER.photoUrl = it
                        APP_ACTIVITY.mNavDrawer.updateHeader()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.settings_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu_change_name -> replaceFragment(ChangeNameFragment())
        }
        return true
    }
}