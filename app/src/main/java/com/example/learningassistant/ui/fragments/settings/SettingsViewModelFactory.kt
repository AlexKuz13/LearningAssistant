package com.example.learningassistant.ui.fragments.settings

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.storage.StorageReference

@Suppress("UNCHECKED_CAST")
class SettingsViewModelFactory( val uri:Uri,val path:StorageReference):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SettingsFragmentViewModel(uri,path) as T
    }
}