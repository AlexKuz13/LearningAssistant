package com.example.learningassistant.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learningassistant.R
import com.example.learningassistant.database.CHILD_TIMESTAMP
import com.example.learningassistant.database.COLL_TASKS
import com.example.learningassistant.database.DB
import com.example.learningassistant.databinding.FragmentMainBinding
import com.example.learningassistant.models.Task
import com.example.learningassistant.ui.adapters.TaskAdapter
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.showToast
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ListenerRegistration


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mRefTasks: CollectionReference
    private lateinit var mAdapter: TaskAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mlistenerTask: ListenerRegistration

    private var mListTasks = emptyList<Task>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        initFields()
        initRecyclerView()
        mBinding.btnCreateTask.setOnClickListener {
            APP_ACTIVITY.navController.navigate(R.id.action_mainFragment_to_createTaskFragment)
        }
    }

    private fun initFields() {
        APP_ACTIVITY.title = "Learning Assistant"
        APP_ACTIVITY.mNavDrawer.enableDrawer()
        mLayoutManager = LinearLayoutManager(this.context)
    }

    private fun initRecyclerView() {
        mRecyclerView = mBinding.taskRecyclerView
        mAdapter = TaskAdapter()
        mRefTasks = DB.collection(COLL_TASKS)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.isNestedScrollingEnabled = false
        mRecyclerView.layoutManager = mLayoutManager

        mlistenerTask = mRefTasks.orderBy(CHILD_TIMESTAMP).addSnapshotListener { value, error ->
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

    override fun onDestroyView() {
        super.onDestroyView()
        mlistenerTask.remove()
        _binding = null
    }
}