package com.example.learningassistant.ui.adapters

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.learningassistant.R
import com.example.learningassistant.database.COLL_USERS
import com.example.learningassistant.database.DB
import com.example.learningassistant.database.UID
import com.example.learningassistant.models.Task
import com.example.learningassistant.models.User
import com.example.learningassistant.utilits.*
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.task_item.view.*
import java.text.DecimalFormat

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.TaskHolder>() {

    private var mlistTasksCache = emptyList<Task>()

    class TaskHolder(view: View) : RecyclerView.ViewHolder(view) {
        val blockTask: ConstraintLayout = view.block_task
        val taskProfilePhoto: CircleImageView = view.task_profile_photo
        val taskProfileFullname: TextView = view.task_profile_fullname
        val taskStarRating: TextView = view.task_profile_rating
        val taskTime: TextView = view.task_time
        val taskTopicName: TextView = view.task_topic_name
        val taskDescriptionText: TextView = view.task_description_text
        val taskBtnHelp: Button = view.btn_task_help

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {

        return TaskHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.task_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        var taskUser: User
        DB.collection(COLL_USERS).document(mlistTasksCache[position].from)
            .get()
            .addOnSuccessListener {
                taskUser = it.toObject(User::class.java) ?: User()
                initHolder(taskUser, holder, position)
            }
            .addOnFailureListener { showToast(it.message.toString()) }


    }


    private fun initHolder(user: User, holder: TaskHolder, position: Int) {
        val bundle = Bundle()
        bundle.putSerializable("User", user)
        holder.taskProfilePhoto.downloadAndSetImage(user.photoUrl)
        holder.taskProfilePhoto.setOnClickListener {
            APP_ACTIVITY.navController.navigate(
                R.id.action_mainFragment_to_settingsFragment,
                bundle
            )
        }
        holder.taskProfileFullname.text = user.fullName
        holder.taskStarRating.text = DecimalFormat("#.##").format(user.rating).toString()
        holder.taskTime.text = mlistTasksCache[position].timeStamp.toString().asTime()
        holder.taskTopicName.text = mlistTasksCache[position].topic
        holder.taskDescriptionText.text = mlistTasksCache[position].description
        holder.taskBtnHelp.setOnClickListener {
            APP_ACTIVITY.navController.navigate(
                R.id.action_mainFragment_to_messagesFragment,
                bundle
            )
        }
        initMyOrNo(position, holder)
    }

    private fun initMyOrNo(
        position: Int,
        holder: TaskHolder
    ) {
        if (mlistTasksCache[position].from == UID) {
            holder.taskBtnHelp.visibility = View.GONE
            holder.blockTask.background = getDrawable(APP_ACTIVITY, R.drawable.task_background_user)
        } else {
            holder.taskBtnHelp.visibility = View.VISIBLE
            holder.blockTask.background = getDrawable(APP_ACTIVITY, R.drawable.task_background)
        }
    }

    override fun getItemCount(): Int = mlistTasksCache.size

    fun setList(list: List<Task>) {
        mlistTasksCache = list
        notifyDataSetChanged()
    }
}
