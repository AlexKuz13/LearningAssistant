package com.example.learningassistant.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.learningassistant.R
import com.example.learningassistant.database.COLL_USERS
import com.example.learningassistant.database.DB
import com.example.learningassistant.database.UID
import com.example.learningassistant.databinding.TaskItemBinding
import com.example.learningassistant.models.Task
import com.example.learningassistant.models.User
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.showToast

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.TaskHolder>() {

    private var mListTasksCache = emptyList<Task>()

    class TaskHolder(private val binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task, user: User, userBoolean: Boolean) {
            binding.user = user
            binding.task = task
            binding.userBoolean = userBoolean
            binding.from = APP_ACTIVITY.resources.getString(R.string.main_fragment)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): TaskHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TaskItemBinding.inflate(layoutInflater, parent, false)
                return TaskHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        return TaskHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        val currentTask = mListTasksCache[position]
        DB.collection(COLL_USERS).document(currentTask.from)
            .get()
            .addOnSuccessListener {
                val taskUser = it.toObject(User::class.java) ?: User()
                var userBoolean = false
                if (taskUser.id == UID) userBoolean = true
                holder.bind(currentTask, taskUser, userBoolean)
            }
            .addOnFailureListener { showToast(it.message.toString()) }
    }


//    private fun initHolder(user: User, holder: TaskHolder, position: Int) {
//        val bundle = Bundle()
//        bundle.putSerializable("User", user)
//        holder.taskProfilePhoto.downloadAndSetImage(user.photoUrl)
//        holder.taskProfilePhoto.setOnClickListener {
//            APP_ACTIVITY.navController.navigate(
//                R.id.action_mainFragment_to_settingsFragment,
//                bundle
//            )
//        }
//        holder.taskProfileFullname.text = user.fullName
//        holder.taskStarRating.text = DecimalFormat("#.##").format(user.rating).toString()
//        holder.taskTime.text = mlistTasksCache[position].timeStamp.toString().asTime()
//        holder.taskSubjectName.text = mlistTasksCache[position].school_subject
//        holder.taskClassValue.text = mlistTasksCache[position].school_class
//        holder.taskDescriptionText.text = mlistTasksCache[position].description
//        holder.taskBtnHelp.setOnClickListener {
//            APP_ACTIVITY.navController.navigate(
//                R.id.action_mainFragment_to_messagesFragment,
//                bundle
//            )
//        }
//        initMyOrNo(position, holder)
//    }


    override fun getItemCount(): Int = mListTasksCache.size

    fun setList(list: List<Task>) {
        mListTasksCache = list
        notifyDataSetChanged()
    }
}
