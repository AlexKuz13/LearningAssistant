package com.example.learningassistant.ui.fragments.my_tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learningassistant.R
import com.example.learningassistant.databinding.FragmentMyTasksBinding
import com.example.learningassistant.models.Task
import com.example.learningassistant.ui.adapters.MyTasksAdapter
import com.example.learningassistant.ui.fragments.BaseFragment
import com.example.learningassistant.utilits.APP_ACTIVITY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyTasksFragment : BaseFragment() {

    private var _binding: FragmentMyTasksBinding? = null
    private val mBinding get() = _binding!!
    private val mAdapter by lazy { MyTasksAdapter() }
    lateinit var mViewModel: MyTasksFragmentViewModel
    private lateinit var mObserverTasks: Observer<List<Task>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyTasksBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(this).get(MyTasksFragmentViewModel::class.java)
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = resources.getString(R.string.fragment_my_tasks)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mBinding.taskRecyclerView.adapter = mAdapter
        mBinding.taskRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        // showShimmerEffect()

        mObserverTasks = Observer {
            val list = it
            mAdapter.setList(list)
            mBinding.taskRecyclerView.smoothScrollToPosition(mAdapter.itemCount)
        }
        mViewModel.listMyTasks().observe(this, mObserverTasks)
    }


    override fun onDestroy() {
        super.onDestroy()
        mViewModel.listMyTasks().removeObserver(mObserverTasks)
        _binding = null
    }
}
