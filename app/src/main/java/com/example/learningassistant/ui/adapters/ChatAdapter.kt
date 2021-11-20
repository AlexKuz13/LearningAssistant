package com.example.learningassistant.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.learningassistant.data.database.COLL_USERS
import com.example.learningassistant.data.database.DB
import com.example.learningassistant.databinding.ChatItemBinding
import com.example.learningassistant.models.Chat
import com.example.learningassistant.models.User
import com.example.learningassistant.utilits.diffutils.ChatDiffUtil
import com.example.learningassistant.utilits.showToast


class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatHolder>() {

    private var mListChatCache = emptyList<Chat>()

    class ChatHolder(private val binding: ChatItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat, user: User) {
            binding.user = user
            binding.chat = chat
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ChatHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ChatItemBinding.inflate(layoutInflater, parent, false)
                return ChatHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        return ChatHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        val currentChat = mListChatCache[position]
        DB.collection(COLL_USERS).document(currentChat.id)
            .get()
            .addOnSuccessListener {
                val chatUser = it.toObject(User::class.java) ?: User()
                holder.bind(currentChat, chatUser)
            }
            .addOnFailureListener { showToast(it.message.toString()) }
    }


    override fun getItemCount(): Int = mListChatCache.size

    fun setList(list: List<Chat>) {
        val chatsDiffUtil = ChatDiffUtil(mListChatCache, list)
        val diffUtilResult = DiffUtil.calculateDiff(chatsDiffUtil)
        mListChatCache = list
        diffUtilResult.dispatchUpdatesTo(this)
    }
}