package com.example.learningassistant.ui.adapters

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.learningassistant.R
import com.example.learningassistant.database.COLL_USERS
import com.example.learningassistant.database.DB
import com.example.learningassistant.database.UID
import com.example.learningassistant.database.USER
import com.example.learningassistant.models.Task
import com.example.learningassistant.models.User
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.asTime
import com.example.learningassistant.utilits.downloadAndSetImage
import com.example.learningassistant.utilits.showToast
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.task_item.view.*

class TaskAdapter:RecyclerView.Adapter<TaskAdapter.TaskHolder>() {

    private var mlistTasksCache = emptyList<Task>()

    class TaskHolder(view: View):RecyclerView.ViewHolder(view){
        val blockTask:ConstraintLayout=view.block_task
        val taskProfilePhoto:CircleImageView=view.task_profile_photo
        val taskProfileFullname: TextView=view.task_profile_fullname
        val taskStarRating:TextView=view.task_profile_rating
        val taskTime:TextView=view.task_time
        val taskTopicName:TextView=view.task_topic_name
        val taskDescriptionText:TextView=view.task_description_text
        val taskBtnHelp:Button=view.btn_task_help
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        return TaskHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.task_item, parent, false))
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        var TaskUser:User
        DB.collection(COLL_USERS).document(mlistTasksCache[position].from).get() // в отдельную функцию
            .addOnSuccessListener {
                 TaskUser =it.toObject(User::class.java) ?: User()
                holder.taskProfilePhoto.downloadAndSetImage(TaskUser.photoUrl)
                holder.taskProfileFullname.text=TaskUser.fullName
                holder.taskStarRating.text=TaskUser.rating.toString()
                holder.taskTime.text=mlistTasksCache[position].timeStamp.toString().asTime()
                holder.taskTopicName.text=mlistTasksCache[position].topic
                holder.taskDescriptionText.text=mlistTasksCache[position].description
            }
            .addOnFailureListener { showToast(it.message.toString()) }
        if(mlistTasksCache[position].from==UID) {
            holder.taskBtnHelp.visibility=View.GONE
            holder.blockTask.background= APP_ACTIVITY.getDrawable(R.drawable.task_background_user)
        }
    }

    override fun getItemCount(): Int = mlistTasksCache.size

    fun setList(list: List<Task>) {
        mlistTasksCache = list
        notifyDataSetChanged()
    }
}
