package com.example.learningassistant.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.learningassistant.R
import com.example.learningassistant.database.TYPE_TEXT
import com.example.learningassistant.database.UID
import com.example.learningassistant.database.sendTask
import com.example.learningassistant.databinding.FragmentCreateTaskBinding
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.showToast
import kotlinx.android.synthetic.main.fragment_create_task.*


class CreateTaskFragment : DialogFragment() {


    private var _binding: FragmentCreateTaskBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentCreateTaskBinding.inflate(layoutInflater,container,false)
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        mBinding.btnSubmitTask.setOnClickListener {
            val topic = mBinding.createTaskEtTopic.text.toString()
            val description = mBinding.createTaskEtDescription.text.toString()
            if (topic.isEmpty() || description.isEmpty()) {
                showToast("Заполните поля")
            } else {
                sendTask(topic, description, UID, TYPE_TEXT) {
                    APP_ACTIVITY.navController.navigate(R.id.action_createTaskFragment_to_mainFragment)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}