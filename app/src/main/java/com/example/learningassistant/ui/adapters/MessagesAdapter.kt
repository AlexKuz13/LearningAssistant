package com.example.learningassistant.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.learningassistant.data.database.COLL_USERS
import com.example.learningassistant.data.database.DB
import com.example.learningassistant.data.database.TYPE_IMAGE
import com.example.learningassistant.data.database.UID
import com.example.learningassistant.databinding.MessageItemBinding
import com.example.learningassistant.models.Message
import com.example.learningassistant.models.User
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
//        when (currentMessage.type_mes) {
//            TYPE_TEXT -> drawMessageText(holder, position)
//            TYPE_IMAGE -> drawMessageImage(holder, position)
//        }
    }

    //  private fun drawMessageText(holder: SingleChatHolder, position: Int) {
//        holder.blockReceivingImageMessage.visibility = View.GONE
//        holder.blockUserImageMessage.visibility = View.GONE
//        if (mlistMessagesCache[position].from == UID) {
//            holder.blockUserMessage.visibility = View.VISIBLE
//            holder.blockReceivingMessage.visibility = View.GONE
//            holder.chatUserMessage.text = mlistMessagesCache[position].text
//            holder.chatUserMessageTime.text = mlistMessagesCache[position]
//                .timeStamp.toString().asTimeMessage()
//        } else {
//            holder.blockUserMessage.visibility = View.GONE
//            holder.blockReceivingMessage.visibility = View.VISIBLE
//            holder.chatReceivingMessage.text = mlistMessagesCache[position].text
//            holder.chatReceivingMessageTime.text = mlistMessagesCache[position]
//                .timeStamp.toString().asTimeMessage()
//        }
    //  }

    //  private fun drawMessageImage(holder: SingleChatHolder, position: Int) {
//        holder.blockUserMessage.visibility = View.GONE
//        holder.blockReceivingMessage.visibility = View.GONE
//        if (mlistMessagesCache[position].from == UID) {
//            holder.blockReceivingImageMessage.visibility = View.GONE
//            holder.blockUserImageMessage.visibility = View.VISIBLE
//            holder.chatUserImageMessage.downloadAndSetImage(
//                mlistMessagesCache[position].imageUrl
//            )
//            holder.chatUserImageTime.text = mlistMessagesCache[position]
//                .timeStamp.toString().asTimeMessage()
//
//        } else {
//            holder.blockReceivingImageMessage.visibility = View.VISIBLE
//            holder.blockUserImageMessage.visibility = View.GONE
//            holder.chatReceivingImageMessage.downloadAndSetImage(
//                mlistMessagesCache[position].imageUrl
//            )
//            holder.chatReceivingImageTime.text = mlistMessagesCache[position]
//                .timeStamp.toString().asTimeMessage()
//
//        }
    //  }

    override fun getItemCount(): Int = mListMessagesCache.size

    fun setList(list: List<Message>) {
        mListMessagesCache = list
        notifyDataSetChanged()
    }

}