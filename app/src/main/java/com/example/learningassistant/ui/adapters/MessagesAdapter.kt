package com.example.learningassistant.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.learningassistant.data.database.COLL_USERS
import com.example.learningassistant.data.database.DB
import com.example.learningassistant.data.database.TYPE_IMAGE
import com.example.learningassistant.data.database.UID
import com.example.learningassistant.databinding.MessageItemBinding
import com.example.learningassistant.models.Message
import com.example.learningassistant.models.User
import com.example.learningassistant.utilits.diffutils.MessageDiffUtil
import com.example.learningassistant.utilits.showToast


class MessagesAdapter : RecyclerView.Adapter<MessagesAdapter.MessageHolder>() {

    private var mListMessagesCache = emptyList<Message>()

    class MessageHolder(private val binding: MessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: Message, user: Boolean, msgTxt: Boolean) {
            binding.message = message
            binding.userBoolean = user
            binding.msgTxt = msgTxt
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MessageHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MessageItemBinding.inflate(layoutInflater, parent, false)
                return MessageHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        return MessageHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        val currentMessage = mListMessagesCache[position]


        DB.collection(COLL_USERS).document(currentMessage.from)
            .get()
            .addOnSuccessListener {
                val taskUser = it.toObject(User::class.java) ?: User()
                var userBoolean = false
                var msgText = true
                if (taskUser.id == UID) userBoolean = true
                if (currentMessage.type_mes == TYPE_IMAGE) msgText = false
                holder.bind(currentMessage, userBoolean, msgText)
            }
            .addOnFailureListener { showToast(it.message.toString()) }
    }


    override fun getItemCount(): Int = mListMessagesCache.size

    fun setList(list: List<Message>) {
        val messagesDiffUtil = MessageDiffUtil(mListMessagesCache, list)
        val diffUtilResult = DiffUtil.calculateDiff(messagesDiffUtil)
        mListMessagesCache = list
        diffUtilResult.dispatchUpdatesTo(this)
    }

}