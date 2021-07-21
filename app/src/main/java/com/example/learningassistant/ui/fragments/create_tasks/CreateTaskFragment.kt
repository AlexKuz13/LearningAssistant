package com.example.learningassistant.ui.fragments.create_tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.learningassistant.R
import com.example.learningassistant.database.TYPE_TEXT
import com.example.learningassistant.database.UID
import com.example.learningassistant.databinding.FragmentCreateTaskBinding
import com.example.learningassistant.models.Task
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.showToast
import com.google.firebase.firestore.FieldValue


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
        mViewModel = ViewModelProvider(this, CreateTaskViewModelFactory(task)).get(
            CreateTaskFragmentViewModel::class.java
        )
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        mBinding.btnSubmitTask.setOnClickListener {
            initTask()
            if (task.topic.isEmpty() || task.description.isEmpty()) {
                showToast("Заполните поля")
            } else {
                mViewModel = ViewModelProvider(this, CreateTaskViewModelFactory(task)).get(
                    CreateTaskFragmentViewModel::class.java
                )
                mViewModel.insertTask {
                    APP_ACTIVITY.navController.navigate(R.id.action_createTaskFragment_to_mainFragment)
                }
            }
        }
    }

    private fun initTask() {
        task.from = UID
        task.topic = mBinding.createTaskEtTopic.text.toString()
        task.description = mBinding.createTaskEtDescription.text.toString()
        task.timeStamp = System.currentTimeMillis()
        task.type_des = TYPE_TEXT
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}