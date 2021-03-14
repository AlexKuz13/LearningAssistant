package com.example.learningassistant.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.learningassistant.R
import com.example.learningassistant.database.COLL_USERS
import com.example.learningassistant.database.DB
import com.example.learningassistant.models.Chat
import com.example.learningassistant.models.User
import com.example.learningassistant.ui.fragments.messages.SingleChatFragment
import com.example.learningassistant.utilits.downloadAndSetImage
import com.example.learningassistant.utilits.replaceFragment
import com.example.learningassistant.utilits.showToast
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.chat_item.view.*

class ChatAdapter:RecyclerView.Adapter<ChatAdapter.ChatHolder>() {

    private var mlistChatCache = emptyList<Chat>()

    class ChatHolder(view: View) : RecyclerView.ViewHolder(view) {
        val chatProfile: ConstraintLayout = view.chat_profile
        val chatProfilePhoto: CircleImageView = view.chat_profile_photo
        val chatProfileFullname: TextView = view.chat_profile_fullname
        val chatProfileMessage: TextView = view.chat_profile_message
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        return ChatHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.chat_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        var ChatUser: User
        DB.collection(COLL_USERS).document(mlistChatCache[position].id)
            .get() // в отдельную функцию
            .addOnSuccessListener {
                ChatUser = it.toObject(User::class.java) ?: User()
                if (ChatUser.photoUrl!="empty") {
                    holder.chatProfilePhoto.downloadAndSetImage(ChatUser.photoUrl)
                }
                holder.chatProfile.setOnClickListener { replaceFragment(SingleChatFragment(ChatUser)) }
                holder.chatProfileFullname.text = ChatUser.fullName
                holder.chatProfileMessage.text = mlistChatCache[position].last_message
            }
            .addOnFailureListener { showToast(it.message.toString()) }
    }

    override fun getItemCount(): Int = mlistChatCache.size

    fun setList(list: List<Chat>) {
        mlistChatCache = list
        notifyDataSetChanged()
    }
}