package com.example.learningassistant.ui.fragments.create_tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.learningassistant.R
import com.example.learningassistant.data.database.TYPE_TEXT
import com.example.learningassistant.data.database.UID
import com.example.learningassistant.databinding.FragmentCreateTaskBinding
import com.example.learningassistant.models.Task
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.showToast


class CreateTaskFragment : DialogFragment() {


    private var _binding: FragmentCreateTaskBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mViewModel: CreateTaskFragmentViewModel
    private var task = Task()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateTaskBinding.inflate(layoutInflater, container, false)
        mViewModel = ViewModelProvider(this).get(CreateTaskFragmentViewModel::class.java)
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()

        initSpinner()

        mBinding.btnSubmitTask.setOnClickListener {
            initTask()
            if (task.description.isEmpty()) {
                showToast(resources.getString(R.string.fill_fields))
            } else {
                mViewModel.insertTask(task) {
                    APP_ACTIVITY.navController.navigate(R.id.action_createTaskFragment_to_mainFragment)
                }
            }
        }
    }

    private fun initSpinner() {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.subject_dropdown_item,
            requireActivity().resources.getStringArray(R.array.subject_array)
        )
        mBinding.tvSubjectDropdown.setAdapter(adapter)
        mBinding.tvSubjectDropdown.setText(
            requireActivity().resources.getString(R.string.maths),
            false
        )

        val adapter2 = ArrayAdapter(
            requireContext(),
            R.layout.subject_dropdown_item,
            requireActivity().resources.getStringArray(R.array.class_array)
        )
        mBinding.tvClassDropdown.setAdapter(adapter2)
        mBinding.tvClassDropdown.setText(
            requireActivity().resources.getString(R.string.class_1),
            false
        )
    }

    private fun initTask() {
        task.from = UID
        task.school_subject = mBinding.tvSubjectDropdown.text.toString()
        task.school_class = mBinding.tvClassDropdown.text.toString()
        task.description = mBinding.createTaskEtDescription.text.toString()
        task.timeStamp = System.currentTimeMillis()
        task.type_des = TYPE_TEXT
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}