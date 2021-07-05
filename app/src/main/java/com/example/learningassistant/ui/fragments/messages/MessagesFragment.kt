package com.example.learningassistant.ui.fragments.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learningassistant.database.CHILD_TIMESTAMP
import com.example.learningassistant.database.COLL_CHATS_ROSTER
import com.example.learningassistant.database.DB
import com.example.learningassistant.database.UID
import com.example.learningassistant.databinding.FragmentMessagesBinding
import com.example.learningassistant.models.Chat
import com.example.learningassistant.ui.adapters.ChatAdapter
import com.example.learningassistant.ui.fragments.BaseFragment
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.showToast
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query

class MessagesFragment : BaseFragment() {

    private var _binding: FragmentMessagesBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mRefChat: CollectionReference
    private lateinit var mAdapter: ChatAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mlistenerChat: ListenerRegistration

    private var mListChat = emptyList<Chat>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentMessagesBinding.inflate(layoutInflater,container,false)
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Сообщения"
        initFields()
        initRecyclerView()
    }


    private fun initFields() {
        mLayoutManager = LinearLayoutManager(this.context)
    }

    private fun initRecyclerView() {
        mRecyclerView = mBinding.taskRecyclerView
        mAdapter = ChatAdapter()
        mRefChat = DB.collection(COLL_CHATS_ROSTER).document(UID).collection("interlocutor")
        mRecyclerView.adapter = mAdapter
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.isNestedScrollingEnabled = false
        mRecyclerView.layoutManager = mLayoutManager

        mlistenerChat = mRefChat.orderBy(CHILD_TIMESTAMP, Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                error?.let {
                    showToast(it.message.toString())
                }
                value?.let {
                    mListChat = it.toObjects(Chat::class.java)
                    mAdapter.setList(mListChat)
                    mRecyclerView.smoothScrollToPosition(mAdapter.itemCount)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mlistenerChat.remove()
        _binding=null
    }
}