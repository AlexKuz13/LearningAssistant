package com.example.learningassistant.ui.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learningassistant.R
import com.example.learningassistant.database.USER
import com.example.learningassistant.databinding.FragmentMainBinding
import com.example.learningassistant.models.Task
import com.example.learningassistant.models.User
import com.example.learningassistant.ui.adapters.TaskAdapter
import com.example.learningassistant.utilits.APP_ACTIVITY


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val mBinding get() = _binding!!
    private val mAdapter by lazy { TaskAdapter() }
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
        mBinding.btnTaskBottomSheet.setOnClickListener {
            APP_ACTIVITY.navController.navigate(R.id.action_mainFragment_to_taskBottomSheet)
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
        mBinding.taskRecyclerView.adapter = mAdapter
        mBinding.taskRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        //showShimmerEffect()


        mObserverTasks = Observer {
            val list = it
            mAdapter.setList(list)
            mBinding.taskRecyclerView.smoothScrollToPosition(mAdapter.itemCount)

        }
        mViewModel.listTasks.observe(this, mObserverTasks)
    }

    private fun showShimmerEffect() {
        mBinding.taskRecyclerView.showShimmer()
    }

    private fun hideShimmerEffect() {
        mBinding.taskRecyclerView.hideShimmer()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        mViewModel.listTasks.removeObserver(mObserverTasks)
        mViewModel.currentUser.removeObserver(mObserverUser)
        _binding = null
    }
}