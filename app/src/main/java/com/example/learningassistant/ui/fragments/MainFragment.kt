package com.example.learningassistant.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.learningassistant.R
import com.example.learningassistant.database.*
import com.example.learningassistant.models.Task
import com.example.learningassistant.ui.adapters.TaskAdapter
import com.example.learningassistant.utilits.*
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var mRefTasks: CollectionReference
    private lateinit var mre:CollectionReference
    private lateinit var mAdapter: TaskAdapter
    private lateinit var mRecyclerView: RecyclerView

    //private lateinit var mTasksListener:EventListener<>
    private var mListTasks = emptyList<Task>()

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Learning Assistant"
        APP_ACTIVITY.mNavDrawer.enableDrawer()
        btn_create_task.setOnClickListener {
            CreateTaskFragment().show(APP_ACTIVITY.supportFragmentManager, "createTaskFragment")
        }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mRecyclerView = task_recycler_view
        mAdapter = TaskAdapter()
        mRefTasks = DB.collection(COLL_TASKS)
        mRecyclerView.adapter = mAdapter
        mRefTasks.addSnapshotListener { value, error ->
            error?.let {
                showToast(it.message.toString())
                return@addSnapshotListener
            }
            value?.let {
                mListTasks = it.toObjects(Task::class.java)
                mAdapter.setList(mListTasks)
                //mRecyclerView.smoothScrollToPosition(mAdapter.itemCount)
            }

        }
    }
}