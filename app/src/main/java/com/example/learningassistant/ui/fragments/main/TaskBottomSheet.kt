package com.example.learningassistant.ui.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.learningassistant.databinding.TaskBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class TaskBottomSheet : BottomSheetDialogFragment() {

    private var _binding: TaskBottomSheetBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TaskBottomSheetBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }


}