package com.example.learningassistant.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningassistant.data.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    var readDarkTheme = dataStoreRepository.readDarkTheme
    var readLangCodeAndId = dataStoreRepository.readLangCodeAndId

    fun saveDarkTheme(boolean: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveDarkTheme(boolean)
        }

}