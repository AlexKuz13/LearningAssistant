package com.example.learningassistant.ui.fragments

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learningassistant.R
import com.example.learningassistant.database.CHILD_TIMESTAMP
import com.example.learningassistant.database.COLL_TASKS
import com.example.learningassistant.database.DB
import com.example.learningassistant.models.Task
import com.example.learningassistant.ui.adapters.TaskAdapter
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.showToast
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var mRefTasks: CollectionReference
    private lateinit var mAdapter: TaskAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mlistenerTask:ListenerRegistration

    private var mListTasks = emptyList<Task>()

    override fun onResume() {
        super.onResume()
        initFields()
        initRecyclerView()
        btn_create_task.setOnClickListener {
            CreateTaskFragment().show(APP_ACTIVITY.supportFragmentManager, "createTaskFragment")
        }
    }

    private fun initFields() {
        APP_ACTIVITY.title = "Learning Assistant"
        APP_ACTIVITY.mNavDrawer.enableDrawer()
        mLayoutManager = LinearLayoutManager(this.context)
    }

    private fun initRecyclerView() {
        mRecyclerView = task_recycler_view
        mAdapter = TaskAdapter()
        mRefTasks = DB.collection(COLL_TASKS)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.isNestedScrollingEnabled=false
        mRecyclerView.layoutManager = mLayoutManager

        mlistenerTask=mRefTasks.orderBy(CHILD_TIMESTAMP).addSnapshotListener { value, error ->
            error?.let {
                showToast(it.message.toString())
            }
            value?.let {
                mListTasks = it.toObjects(Task::class.java)
                mAdapter.setList(mListTasks)
                mRecyclerView.smoothScrollToPosition(mAdapter.itemCount)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        mlistenerTask.remove()
    }
}