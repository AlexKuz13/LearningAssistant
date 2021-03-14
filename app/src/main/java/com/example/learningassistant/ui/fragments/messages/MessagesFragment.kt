package com.example.learningassistant.ui.fragments.messages

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learningassistant.R
import com.example.learningassistant.database.*
import com.example.learningassistant.models.Chat
import com.example.learningassistant.models.Task
import com.example.learningassistant.ui.adapters.ChatAdapter
import com.example.learningassistant.ui.adapters.TaskAdapter
import com.example.learningassistant.ui.fragments.BaseFragment
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.showToast
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_main.*

class MessagesFragment : BaseFragment(R.layout.fragment_messages) {

    private lateinit var mRefChat: CollectionReference
    private lateinit var mAdapter: ChatAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mlistenerChat: ListenerRegistration

    private var mListChat = emptyList<Chat>()


    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title="Сообщения"
        initFields()
        initRecyclerView()
    }


    private fun initFields() {
        APP_ACTIVITY.title="Сообщения"
        mLayoutManager = LinearLayoutManager(this.context)
    }

    private fun initRecyclerView() {
        mRecyclerView = task_recycler_view
        mAdapter = ChatAdapter()
        mRefChat = DB.collection(COLL_CHATS_ROSTER).document(UID).collection("interlocutor")
        mRecyclerView.adapter = mAdapter
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.isNestedScrollingEnabled=false
        mRecyclerView.layoutManager = mLayoutManager

        mlistenerChat=mRefChat.orderBy(CHILD_TIMESTAMP,Query.Direction.DESCENDING).addSnapshotListener { value, error ->
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

    override fun onPause() {
        super.onPause()
        mlistenerChat.remove()
    }
}