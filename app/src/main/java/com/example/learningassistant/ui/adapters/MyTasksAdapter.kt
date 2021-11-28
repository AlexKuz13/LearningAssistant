package com.example.learningassistant.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.learningassistant.databinding.MyTaskItemBinding
import com.example.learningassistant.models.Task
import com.example.learningassistant.utilits.diffutils.TaskDiffUtil

class MyTasksAdapter : RecyclerView.Adapter<MyTasksAdapter.MyTasksHolder>() {

    private var mListMyTasksCache = emptyList<Task>()

    class MyTasksHolder(private val binding: MyTaskItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.task = task
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyTasksHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MyTaskItemBinding.inflate(layoutInflater, parent, false)
                return MyTasksHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTasksHolder {
        return MyTasksHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyTasksHolder, position: Int) {
        val currentTask = mListMyTasksCache[position]
        holder.bind(currentTask)
    }

    override fun getItemCount(): Int = mListMyTasksCache.size

    fun setList(list: List<Task>) {
        val tasksDiffUtil = TaskDiffUtil(mListMyTasksCache, list)
        val diffUtilResult = DiffUtil.calculateDiff(tasksDiffUtil)
        mListMyTasksCache = list
        diffUtilResult.dispatchUpdatesTo(this)
    }
}