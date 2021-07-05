package com.example.learningassistant.ui.fragments.messages

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learningassistant.R
import com.example.learningassistant.database.*
import com.example.learningassistant.databinding.FragmentSingleChatBinding
import com.example.learningassistant.models.Message
import com.example.learningassistant.models.User
import com.example.learningassistant.ui.adapters.SingleChatAdapter
import com.example.learningassistant.ui.fragments.BaseFragment
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.AppTextWatcher
import com.example.learningassistant.utilits.downloadAndSetImage
import com.example.learningassistant.utilits.showToast
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ListenerRegistration
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_single_chat.*
import kotlinx.android.synthetic.main.toolbar_info.view.*


class SingleChatFragment : BaseFragment() {

    private var _binding: FragmentSingleChatBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mRefMessages: CollectionReference
    private lateinit var mAdapter: SingleChatAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mToolbarInfo: View
    private lateinit var mlistenerToolbar: ListenerRegistration
    private lateinit var mlistenerMessage: ListenerRegistration
    private lateinit var menuRating: Menu
    private lateinit var human: User


    private val bundle = Bundle()
    private var mUser = User()
    private var mListMessages = emptyList<Message>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSingleChatBinding.inflate(layoutInflater, container, false)
        human = arguments?.getSerializable("User") as User
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        initFields()
        initToolbar()
        initRecyclerView()

    }


    private fun initFields() {
        bundle.putSerializable("User", human)
        mLayoutManager = LinearLayoutManager(this.context)
        mBinding.chatInputMessage.addTextChangedListener(AppTextWatcher {
            val string = mBinding.chatInputMessage.text.toString()
            if (string.isEmpty()) {
                mBinding.chatBtnSendMessage.visibility = View.GONE
                mBinding.chatBtnAttach.visibility = View.VISIBLE
            } else {
                mBinding.chatBtnAttach.visibility = View.GONE
                mBinding.chatBtnSendMessage.visibility = View.VISIBLE
            }
        })

        mBinding.chatBtnAttach.setOnClickListener { attachFile() }
        mBinding.chatBtnSendMessage.setOnClickListener {
            val message = mBinding.chatInputMessage.text.toString()
            val messageKey = DB.collection(COLL_MESSAGES).document().id
            sendMessage(message, human.id, messageKey) {
                addToRoster(human.id)
                mBinding.chatInputMessage.setText("")
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
            resultCode == Activity.RESULT_OK && data != null
        ) {
            val uri = CropImage.getActivityResult(data).uri
            val messageKey = DB.collection(COLL_MESSAGES).document().id
            val path = REF_STORAGE_ROOT.child(FOLDER_PROFILE_IMAGE)
                .child(messageKey)
            putImageToStorage(uri, path) {
                getUrlFromStorage(path) {
                    sendMessageAsImage(human.id, it, messageKey)
                }
            }
        }
    }

    private fun initToolbar() {
        mToolbarInfo = APP_ACTIVITY.main_toolbar.toolbar_info
        mToolbarInfo.visibility = View.VISIBLE

        mlistenerToolbar =
            DB.collection(COLL_USERS).document(human.id).addSnapshotListener { value, error ->
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
        mToolbarInfo.toolbar_chat_image.setOnClickListener {
            APP_ACTIVITY.navController.navigate(
                R.id.action_singleChatFragment_to_settingsFragment,
                bundle
            )
        }
    }
    private fun initRecyclerView() {
        mRecyclerView = chat_recycler_view
        mAdapter = SingleChatAdapter()
        mRefMessages = DB.collection(COLL_MESSAGES).document(UID).collection(human.id)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.isNestedScrollingEnabled = false
        mRecyclerView.layoutManager = mLayoutManager

        mlistenerMessage =
            mRefMessages.orderBy(CHILD_TIMESTAMP).addSnapshotListener { value, error ->
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
        menuRating = menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.user_rating -> APP_ACTIVITY.navController.navigate(R.id.action_singleChatFragment_to_ratingFragment,bundle)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mToolbarInfo.visibility = View.GONE
        mlistenerToolbar.remove()
        mlistenerMessage.remove()
        _binding = null
    }
}
