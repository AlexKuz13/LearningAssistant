package com.example.learningassistant.ui.fragments.messages

import android.app.Activity
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learningassistant.R
import com.example.learningassistant.database.*
import com.example.learningassistant.models.Message
import com.example.learningassistant.models.Task
import com.example.learningassistant.models.User
import com.example.learningassistant.ui.adapters.SingleChatAdapter
import com.example.learningassistant.ui.fragments.BaseFragment
import com.example.learningassistant.ui.fragments.RatingFragment
import com.example.learningassistant.ui.fragments.settings.ChangeNameFragment
import com.example.learningassistant.ui.fragments.settings.SettingsFragment
import com.example.learningassistant.utilits.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ListenerRegistration
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_single_chat.*
import kotlinx.android.synthetic.main.toolbar_info.*
import kotlinx.android.synthetic.main.toolbar_info.view.*


class SingleChatFragment(private val human: User) : BaseFragment(R.layout.fragment_single_chat) {
    private lateinit var mRefMessages: CollectionReference
    private lateinit var mAdapter: SingleChatAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mToolbarInfo: View
    private lateinit var mlistenerToolbar:ListenerRegistration
    private lateinit var mlistenerMessage:ListenerRegistration

    private var mUser = User()
    private var mListMessages= emptyList<Message>()

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        initFields()
        initToolbar()
        initRecyclerView()

    }


    private fun initFields() {
        mLayoutManager = LinearLayoutManager(this.context)
        chat_input_message.addTextChangedListener(AppTextWatcher{
            val string=chat_input_message.text.toString()
            if(string.isEmpty()){
                chat_btn_send_message.visibility=View.GONE
                chat_btn_attach.visibility=View.VISIBLE
            } else{
                chat_btn_attach.visibility=View.GONE
                chat_btn_send_message.visibility=View.VISIBLE
            }
        })

        chat_btn_attach.setOnClickListener { attachFile() }
        chat_btn_send_message.setOnClickListener {
            val message = chat_input_message.text.toString()
            val messageKey = DB.collection(COLL_MESSAGES).document().id
            sendMessage(message, human.id, messageKey) {
                chat_input_message.setText("")
            }
        }
    }

    private fun attachFile() {
        CropImage.activity()
            .setAspectRatio(1, 1)
            .setRequestedSize(600, 600)
            .start(APP_ACTIVITY, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE &&
            resultCode == Activity.RESULT_OK && data != null){
            val uri = CropImage.getActivityResult(data).uri
            val messageKey = DB.collection(COLL_MESSAGES).document().id
            val path = REF_STORAGE_ROOT.child(FOLDER_PROFILE_IMAGE)
                .child(messageKey)
            putImageToStorage(uri,path){
                getUrlFromStorage(path){
                    sendMessageAsImage(human.id,it,messageKey)
                }
            }
        }
    }

    private fun initToolbar() {
        mToolbarInfo = APP_ACTIVITY.main_toolbar.toolbar_info
        mToolbarInfo.visibility = View.VISIBLE

         mlistenerToolbar=DB.collection(COLL_USERS).document(human.id).addSnapshotListener { value, error ->
            error?.let {
                showToast(it.message.toString())
            }
            value?.let {
                mUser = it.toObject(User::class.java) ?: User()
                initInfoToolbar(mUser)
            }
        }
    }


    private fun initInfoToolbar(mUser: User) {
        mToolbarInfo.toolbar_chat_fullname.text = mUser.fullName
        mToolbarInfo.toolbar_chat_image.downloadAndSetImage(mUser.photoUrl)
        mToolbarInfo.toolbar_chat_status.text = mUser.status
        mToolbarInfo.toolbar_chat_image.setOnClickListener { replaceFragment(SettingsFragment(human)) }
    }

    private fun initRecyclerView() {
        mRecyclerView = chat_recycler_view
        mAdapter = SingleChatAdapter()
        mRefMessages=DB.collection(COLL_MESSAGES).document(UID).collection(human.id)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.isNestedScrollingEnabled=false
        mRecyclerView.layoutManager = mLayoutManager

        mlistenerMessage=mRefMessages.orderBy(CHILD_TIMESTAMP).addSnapshotListener { value, error ->
            error?.let {
                showToast(it.message.toString())
            }
            value?.let {
                mListMessages = it.toObjects(Message::class.java)
                mAdapter.setList(mListMessages)
                mRecyclerView.smoothScrollToPosition(mAdapter.itemCount)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.user_rating, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.user_rating -> RatingFragment(human).show(APP_ACTIVITY.supportFragmentManager,"rating_fragment")
        }
        return true
    }

    override fun onPause() {
        super.onPause()
        mToolbarInfo.visibility = View.GONE
        mlistenerToolbar.remove()
        mlistenerMessage.remove()

    }
}
