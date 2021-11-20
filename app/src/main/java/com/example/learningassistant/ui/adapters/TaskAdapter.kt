package com.example.learningassistant.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.learningassistant.R
import com.example.learningassistant.data.database.COLL_USERS
import com.example.learningassistant.data.database.DB
import com.example.learningassistant.data.database.UID
import com.example.learningassistant.databinding.TaskItemBinding
import com.example.learningassistant.models.Task
import com.example.learningassistant.models.User
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.diffutils.TaskDiffUtil
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


    override fun getItemCount(): Int = mListTasksCache.size

    fun setList(list: List<Task>) {
        val tasksDiffUtil = TaskDiffUtil(mListTasksCache, list)
        val diffUtilResult = DiffUtil.calculateDiff(tasksDiffUtil)
        mListTasksCache = list
        diffUtilResult.dispatchUpdatesTo(this)
    }
}
