package com.example.learningassistant.ui.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.learningassistant.R
import com.example.learningassistant.databinding.LanguageBottomSheetBinding
import com.example.learningassistant.utilits.DEFAULT_LANGUAGE
import com.example.learningassistant.utilits.DEFAULT_LANG_CODE
import com.example.learningassistant.utilits.observeOnce
import com.example.learningassistant.utilits.setLocal
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LanguageBottomSheet : BottomSheetDialogFragment() {

    private var _binding: LanguageBottomSheetBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var settingsFragmentViewModel: SettingsFragmentViewModel

    private var langCode = DEFAULT_LANG_CODE
    private var langRBId = 0
    private var language = DEFAULT_LANGUAGE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingsFragmentViewModel =
            ViewModelProvider(requireActivity()).get(SettingsFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LanguageBottomSheetBinding.inflate(inflater, container, false)

        settingsFragmentViewModel.readLangCodeAndId.asLiveData()
            .observeOnce(viewLifecycleOwner, { value ->
                langCode = value.langCode
                langRBId = value.langRBId
                language = value.language
                updateRB(value.langCode)
            })


        mBinding.langRbGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.lang_russian -> setLocalTemp(
                    "ru",
                    checkedId,
                    resources.getString(R.string.lang_russian)
                )
                R.id.lang_english -> setLocalTemp(
                    "en",
                    checkedId,
                    resources.getString(R.string.lang_english)
                )
                R.id.lang_german -> setLocalTemp(
                    "de",
                    checkedId,
                    resources.getString(R.string.lang_german)
                )
                R.id.lang_french -> setLocalTemp(
                    "fr",
                    checkedId,
                    resources.getString(R.string.lang_french)
                )
            }
        }

        mBinding.applyBtn.setOnClickListener {
            settingsFragmentViewModel.saveLangCodeAndId(langCode, langRBId, language)
            findNavController().navigate(R.id.action_languageBottomSheet_to_mainFragment)
        }

        return mBinding.root
    }

    private fun updateRB(lang: String) {
        when (lang) {
            "en" -> mBinding.langEnglish.isChecked = true
            "ru" -> mBinding.langRussian.isChecked = true
            "de" -> mBinding.langGerman.isChecked = true
            "fr" -> mBinding.langFrench.isChecked = true
        }
    }

    private fun setLocalTemp(langCode: String, langRBId: Int, language: String) {
        setLocal(langCode, requireActivity())
        this.langCode = langCode
        this.langRBId = langRBId
        this.language = language
    }
}
