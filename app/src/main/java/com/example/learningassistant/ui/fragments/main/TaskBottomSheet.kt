package com.example.learningassistant.ui.fragments.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.learningassistant.databinding.TaskBottomSheetBinding
import com.example.learningassistant.utilits.DEFAULT_SCHOOL_CLASS
import com.example.learningassistant.utilits.DEFAULT_SCHOOL_SUBJECT
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskBottomSheet : BottomSheetDialogFragment() {

    private var _binding: TaskBottomSheetBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mainFragmentViewModel: MainFragmentViewModel

    private var schoolSubjectChip = DEFAULT_SCHOOL_SUBJECT
    private var schoolSubjectChipId = 0
    private var schoolClassChip = DEFAULT_SCHOOL_CLASS
    private var schoolClassChipId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainFragmentViewModel =
            ViewModelProvider(requireActivity()).get(MainFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TaskBottomSheetBinding.inflate(layoutInflater, container, false)


        mainFragmentViewModel.readSubjectAndClass.asLiveData()
            .observe(viewLifecycleOwner, { value ->
                schoolSubjectChip = value.schoolSubject
                schoolClassChip = value.schoolClass
                updateChip(value.schoolSubjectId, mBinding.subjectChipGroup)
                updateChip(value.schoolClassId, mBinding.classChipGroup)
            })


        mBinding.subjectChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedSchoolSubject = chip.text.toString()
            schoolSubjectChip = selectedSchoolSubject
            schoolSubjectChipId = selectedChipId
        }

        mBinding.classChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedSchoolClass = chip.text.toString()
            schoolClassChip = selectedSchoolClass
            schoolClassChipId = selectedChipId
        }

        mBinding.applyBtn.setOnClickListener {
            mainFragmentViewModel.saveSubjectAndClassTemp(
                schoolSubjectChip,
                schoolSubjectChipId,
                schoolClassChip,
                schoolClassChipId
            )
            mainFragmentViewModel.saveSubjectAndClass()
            val action =
                TaskBottomSheetDirections.actionTaskBottomSheetToMainFragment()
            findNavController().navigate(action)
        }

        return mBinding.root
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            try {
                val selectedChip = chipGroup.findViewById<Chip>(chipId)
                selectedChip.isChecked = true
                chipGroup.requestChildFocus(selectedChip, selectedChip)
            } catch (e: Exception) {
                Log.d("RecipesBottomSheet", e.message.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}