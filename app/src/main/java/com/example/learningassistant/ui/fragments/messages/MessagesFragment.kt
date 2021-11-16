package com.example.learningassistant.ui.fragments.messages

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learningassistant.R
import com.example.learningassistant.data.database.*
import com.example.learningassistant.databinding.FragmentMessagesBinding
import com.example.learningassistant.databinding.ToolbarInfoBinding
import com.example.learningassistant.models.Chat
import com.example.learningassistant.models.Message
import com.example.learningassistant.models.User
import com.example.learningassistant.ui.adapters.MessagesAdapter
import com.example.learningassistant.ui.fragments.BaseFragment
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.AppTextWatcher
import com.example.learningassistant.utilits.showToast
import com.google.firebase.firestore.ListenerRegistration
import com.theartofdev.edmodo.cropper.CropImage


class MessagesFragment : BaseFragment() {

    private val args by navArgs<MessagesFragmentArgs>()

    private var _binding: FragmentMessagesBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var toolbarInfo: View
    private val mAdapter by lazy { MessagesAdapter() }
    private lateinit var toolbarInfoBinding: ToolbarInfoBinding
    private lateinit var mlistenerToolbar: ListenerRegistration
    private lateinit var menuRating: Menu
    private lateinit var human: User

    private lateinit var mViewModel: MessagesFragmentViewModel

    lateinit var mObserverMessages: Observer<List<Message>>
    private val bundle = Bundle()
    private var mUser = User()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessagesBinding.inflate(layoutInflater, container, false)
        human = args.user
        mViewModel = ViewModelProvider(this, MessagesViewModelFactory(human.id)).get(
            MessagesFragmentViewModel::class.java
        )
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        initRecyclerView()
        initFields()
        initToolbar()
    }


    private fun initFields() {
        // bundle.putSerializable("User", human)

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
            initTxtMessage(message)
            mViewModel.sendMessage(MESSAGE) {
                initChat(message)
                mViewModel.addChat(CHAT) {
                    mBinding.chatInputMessage.setText("")
                }
            }
        }
    }

    private fun initChat(msgTxt: String) {
        CHAT = Chat()
        CHAT.last_message = msgTxt
        CHAT.timeStamp = System.currentTimeMillis()
    }

    private fun initTxtMessage(msgTxt: String) {
        MESSAGE = Message()
        MESSAGE.from = UID
        MESSAGE.text = msgTxt
        MESSAGE.timeStamp = System.currentTimeMillis()
        MESSAGE.type_mes = TYPE_TEXT
    }

    private fun initImageMessage(url: String) {
        MESSAGE = Message()
        MESSAGE.from = UID
        MESSAGE.imageUrl = url
        MESSAGE.timeStamp = System.currentTimeMillis()
        MESSAGE.type_mes = TYPE_IMAGE
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
                    initImageMessage(it)
                    mViewModel.sendMessage(MESSAGE) {
                        initChat(requireActivity().resources.getString(R.string.image))
                        mViewModel.addChat(CHAT) {
                            showToast(resources.getString(R.string.sent_image))
                        }
                    }
                }
            }
        }
    }

    private fun initToolbar() {
        toolbarInfoBinding = APP_ACTIVITY.mBinding.toolbarInfo
        toolbarInfo = toolbarInfoBinding.root
        toolbarInfo.visibility = View.VISIBLE

        mlistenerToolbar =
            DB.collection(COLL_USERS).document(human.id).addSnapshotListener { value, error ->
                error?.let {
                    showToast(it.message.toString())
                }
                value?.let {
                    mUser = it.toObject(User::class.java) ?: User()
                    toolbarInfoBinding.user = mUser
                    toolbarInfoBinding.from =
                        APP_ACTIVITY.resources.getString(R.string.messages_fragment)
                }
            }
    }


    private fun initRecyclerView() {
        mBinding.messageRecyclerView.adapter = mAdapter
        mBinding.messageRecyclerView.layoutManager = LinearLayoutManager(requireContext())


        mObserverMessages = Observer {
            val list = it
            mAdapter.setList(list)
            mBinding.messageRecyclerView.smoothScrollToPosition(mAdapter.itemCount)
        }
        mViewModel.listMessages.observe(this, mObserverMessages)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.user_rating, menu)
        menuRating = menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.user_rating -> APP_ACTIVITY.navController.navigate(
                R.id.action_singleChatFragment_to_ratingFragment,
                bundle
            )
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showShimmerEffect() {
        mBinding.messageRecyclerView.showShimmer()
    }

    private fun hideShimmerEffect() {
        mBinding.messageRecyclerView.hideShimmer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        toolbarInfo.visibility = View.GONE
        mlistenerToolbar.remove()
        mViewModel.listMessages.removeObserver(mObserverMessages)
        _binding = null
    }
}
