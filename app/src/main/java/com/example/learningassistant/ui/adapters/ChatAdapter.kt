package com.example.learningassistant.ui.adapters

import android.os.Bundle
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
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.downloadAndSetImage
import com.example.learningassistant.utilits.showToast
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.chat_item.view.*

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatHolder>() {

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
            .get()
            .addOnSuccessListener {
                ChatUser = it.toObject(User::class.java) ?: User()
                initHolder(ChatUser, holder, position)
            }
            .addOnFailureListener { showToast(it.message.toString()) }
    }


    private fun initHolder(user: User, holder: ChatHolder, position: Int) {
        holder.chatProfilePhoto.downloadAndSetImage(user.photoUrl)
        holder.chatProfile.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("User", user)
            APP_ACTIVITY.navController.navigate(
                R.id.action_chatsFragment_to_messagesFragment,
                bundle
            )
        }
        holder.chatProfileFullname.text = user.fullName
        holder.chatProfileMessage.text = mlistChatCache[position].last_message
    }

    override fun getItemCount(): Int = mlistChatCache.size

    fun setList(list: List<Chat>) {
        mlistChatCache = list
        notifyDataSetChanged()
    }
}