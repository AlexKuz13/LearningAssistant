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
import com.example.learningassistant.utilits.showToast
import kotlinx.android.synthetic.main.fragment_create_task.*


class CreateTaskFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_task, container, false)
    }

    override fun onResume() {
        super.onResume()
        btn_submit_task.setOnClickListener {
            val topic=create_task_et_topic.text.toString()
            val description=create_task_et_description.text.toString()
            if (topic.isEmpty() || description.isEmpty()) {
                showToast("Заполните поля")
            } else {
                sendTask(topic,description,UID,TYPE_TEXT) {
                    dismiss()
                }
            }
        }
    }

}