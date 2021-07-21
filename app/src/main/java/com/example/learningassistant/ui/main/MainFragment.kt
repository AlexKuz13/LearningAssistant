package com.example.learningassistant.ui.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learningassistant.R
import com.example.learningassistant.database.CHILD_TIMESTAMP
import com.example.learningassistant.database.COLL_TASKS
import com.example.learningassistant.database.DB
import com.example.learningassistant.database.USER
import com.example.learningassistant.databinding.FragmentMainBinding
import com.example.learningassistant.models.Task
import com.example.learningassistant.models.User
import com.example.learningassistant.ui.adapters.TaskAdapter
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.showToast
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ListenerRegistration


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mAdapter: TaskAdapter
    private lateinit var mRecyclerView: RecyclerView
    lateinit var mViewModel: MainFragmentViewModel
    lateinit var mObserverUser: Observer<User>
    lateinit var mObserverTasks: Observer<List<Task>>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        mViewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initUser {
            initFields()
        }
        initRecyclerView()
        mBinding.btnCreateTask.setOnClickListener {
            APP_ACTIVITY.navController.navigate(R.id.action_mainFragment_to_createTaskFragment)
        }
    }

    private fun initUser(onSuccess: () -> Unit) {
        mObserverUser = Observer {
            USER = it
            onSuccess()
        }
        mViewModel.currentUser.observe(this, mObserverUser)
    }


    private fun initFields() {
        APP_ACTIVITY.mToolbar.visibility = View.VISIBLE
        APP_ACTIVITY.title = "Learning Assistant"
        APP_ACTIVITY.mNavDrawer.enableDrawer()
        APP_ACTIVITY.mNavDrawer.updateHeader()
    }


    private fun initRecyclerView() {
        mRecyclerView = mBinding.taskRecyclerView
        mAdapter = TaskAdapter()
        mRecyclerView.adapter = mAdapter
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.isNestedScrollingEnabled = false

        mObserverTasks = Observer {
            val list = it
            mAdapter.setList(list)
            mRecyclerView.smoothScrollToPosition(mAdapter.itemCount)
        }
        mViewModel.listTasks.observe(this, mObserverTasks)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mViewModel.listTasks.removeObserver(mObserverTasks)
        mViewModel.currentUser.removeObserver(mObserverUser)
        _binding = null
    }
}