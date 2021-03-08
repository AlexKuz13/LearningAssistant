package com.example.learningassistant.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.learningassistant.R
import com.example.learningassistant.database.TYPE_IMAGE
import com.example.learningassistant.database.TYPE_TEXT
import com.example.learningassistant.database.UID
import com.example.learningassistant.models.Message
import com.example.learningassistant.utilits.asTime
import com.example.learningassistant.utilits.asTimeMessage
import com.example.learningassistant.utilits.downloadAndSetImage
import com.example.learningassistant.utilits.showToast
import kotlinx.android.synthetic.main.message_item.view.*

class SingleChatAdapter:RecyclerView.Adapter<SingleChatAdapter.SingleChatHolder>() {

    private var mlistMessagesCache = emptyList<Message>()

    class SingleChatHolder(view: View) : RecyclerView.ViewHolder(view) {
        val blockUserMessage: ConstraintLayout = view.block_user_message
        val chatUserMessage: TextView = view.chat_user_message
        val chatUserMessageTime: TextView = view.chat_user_message_time

        val blockReceivingMessage: ConstraintLayout = view.block_receiving_message
        val chatReceivingMessage: TextView = view.chat_receiving_message
        val chatReceivingMessageTime: TextView = view.chat_receiving_message_time


        val blockUserImageMessage: ConstraintLayout = view.block_user_image
        val chatUserImageMessage: ImageView = view.chat_user_image
        val chatUserImageTime: TextView = view.chat_user_image_time

        val blockReceivingImageMessage: ConstraintLayout = view.block_receiving_image
        val chatReceivingImageMessage: ImageView = view.chat_receiving_image
        val chatReceivingImageTime: TextView = view.chat_receiving_image_time

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleChatHolder {
        return SingleChatHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.message_item, parent, false))
    }

    override fun onBindViewHolder(holder: SingleChatHolder, position: Int) {
        when(mlistMessagesCache[position].type_mes){
            TYPE_TEXT -> drawMessageText(holder,position)
            TYPE_IMAGE -> drawMessageImage(holder,position)
        }
    }

    private fun drawMessageText(holder: SingleChatHolder, position: Int) {
        holder.blockReceivingImageMessage.visibility=View.GONE
        holder.blockUserImageMessage.visibility=View.GONE
        if (mlistMessagesCache[position].from == UID) {
            holder.blockUserMessage.visibility = View.VISIBLE
            holder.blockReceivingMessage.visibility = View.GONE
            holder.chatUserMessage.text = mlistMessagesCache[position].text
            android.os.Handler().postDelayed({
                holder.chatUserMessageTime.text = mlistMessagesCache[position]
                    .timeStamp.toString().asTimeMessage()
            }, 500)
        } else {
            holder.blockUserMessage.visibility = View.GONE
            holder.blockReceivingMessage.visibility = View.VISIBLE
            holder.chatReceivingMessage.text = mlistMessagesCache[position].text
            android.os.Handler().postDelayed({
                holder.chatReceivingMessageTime.text = mlistMessagesCache[position]
                    .timeStamp.toString().asTimeMessage()
            }, 1000)
        }
    }

    private fun drawMessageImage(holder: SingleChatHolder, position: Int) {
        holder.blockUserMessage.visibility = View.GONE
        holder.blockReceivingMessage.visibility = View.GONE
        if (mlistMessagesCache[position].from == UID) {
            holder.blockReceivingImageMessage.visibility=View.GONE
            holder.blockUserImageMessage.visibility=View.VISIBLE
            holder.chatUserImageMessage.downloadAndSetImage(
                mlistMessagesCache[position].imageUrl)
            android.os.Handler().postDelayed({
                holder.chatUserImageTime.text = mlistMessagesCache[position]
                    .timeStamp.toString().asTimeMessage()
            }, 1000)
        } else {
            holder.blockReceivingImageMessage.visibility=View.VISIBLE
            holder.blockUserImageMessage.visibility=View.GONE
            holder.chatReceivingImageMessage.downloadAndSetImage(
                mlistMessagesCache[position].imageUrl)
            android.os.Handler().postDelayed({
                holder.chatReceivingImageTime.text = mlistMessagesCache[position]
                    .timeStamp.toString().asTimeMessage()
            }, 1000)

        }
    }

    override fun getItemCount(): Int =mlistMessagesCache.size

    fun setList(list: List<Message>) {
        mlistMessagesCache = list
        notifyDataSetChanged()
    }

}