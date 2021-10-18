package com.example.learningassistant.ui.fragments.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.learningassistant.databinding.FragmentChatsBinding
import com.example.learningassistant.models.Chat
import com.example.learningassistant.ui.adapters.ChatAdapter
import com.example.learningassistant.ui.fragments.BaseFragment
import com.example.learningassistant.utilits.APP_ACTIVITY

class ChatsFragment : BaseFragment() {

    private var _binding: FragmentChatsBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mAdapter: ChatAdapter
    private lateinit var mRecyclerView: RecyclerView
    lateinit var mViewModel: ChatsFragmentViewModel
    lateinit var mObserverChats: Observer<List<Chat>>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatsBinding.inflate(layoutInflater, container, false)
        mViewModel = ViewModelProvider(this).get(ChatsFragmentViewModel::class.java)
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Сообщения"
        initRecyclerView()
    }


    private fun initRecyclerView() {
        mRecyclerView = mBinding.taskRecyclerView
        mAdapter = ChatAdapter()
        mRecyclerView.adapter = mAdapter
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.isNestedScrollingEnabled = false

        mObserverChats = Observer {
            val list = it
            mAdapter.setList(list)
            mRecyclerView.smoothScrollToPosition(mAdapter.itemCount)
        }
        mViewModel.listChats.observe(this, mObserverChats)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mViewModel.listChats.removeObserver(mObserverChats)
        _binding = null
    }
}