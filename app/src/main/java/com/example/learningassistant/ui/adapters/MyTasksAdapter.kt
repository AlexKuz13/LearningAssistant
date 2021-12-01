package com.example.learningassistant.ui.adapters

import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.learningassistant.R
import com.example.learningassistant.databinding.MyTaskItemBinding
import com.example.learningassistant.models.Task
import com.example.learningassistant.ui.fragments.my_tasks.MyTasksFragmentViewModel
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.diffutils.TaskDiffUtil
import com.google.android.material.snackbar.Snackbar

class MyTasksAdapter(
    private val requireActivity: FragmentActivity,
    private val mViewModel: MyTasksFragmentViewModel
) : RecyclerView.Adapter<MyTasksAdapter.MyTasksHolder>(), ActionMode.Callback {

    private var mListMyTasksCache = emptyList<Task>()
    private var multiSelection = false
    private var selectedTasks = arrayListOf<Task>()
    private var myViewHolders = arrayListOf<MyTasksHolder>()

    private lateinit var mActionMode: ActionMode

    private lateinit var rootView: View

    class MyTasksHolder(val binding: MyTaskItemBinding) :
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
        rootView = holder.itemView.rootView
        myViewHolders.add(holder)
        val currentTask = mListMyTasksCache[position]
        holder.bind(currentTask)

        saveItemStateOnScroll(currentTask, holder)

        /**
         * Single Click Listener
         * */
        holder.binding.blockMyTask.setOnClickListener {
            if (multiSelection) {
                applySelection(holder, currentTask)
            }
        }
        /**
         * Long Click Listener
         * */
        holder.binding.blockMyTask.setOnLongClickListener {
            if (!multiSelection) {
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holder, currentTask)
                true
            } else {
                applySelection(holder, currentTask)
                true
            }
        }
    }

    override fun getItemCount(): Int = mListMyTasksCache.size

    fun setList(list: List<Task>) {
        val tasksDiffUtil = TaskDiffUtil(mListMyTasksCache, list)
        val diffUtilResult = DiffUtil.calculateDiff(tasksDiffUtil)
        mListMyTasksCache = list
        diffUtilResult.dispatchUpdatesTo(this)
    }

    private fun saveItemStateOnScroll(currentTask: Task, holder: MyTasksHolder) {
        if (selectedTasks.contains(currentTask)) {
            changeRecipeStyle(holder, R.color.purple_500)
        } else {
            changeRecipeStyle(holder, R.color.strokeColor)
        }
    }

    private fun applySelection(holder: MyTasksHolder, currentTask: Task) {
        if (selectedTasks.contains(currentTask)) {
            selectedTasks.remove(currentTask)
            changeRecipeStyle(holder, R.color.strokeColor)
            applyActionModeTitle()
        } else {
            selectedTasks.add(currentTask)
            changeRecipeStyle(holder, R.color.purple_500)
            applyActionModeTitle()
        }
    }

    private fun applyActionModeTitle() {
        when (selectedTasks.size) {
            0 -> {
                multiSelection = false
                mActionMode.finish()
            }
            1 -> mActionMode.title = "${selectedTasks.size} item selected"
            else -> mActionMode.title = "${selectedTasks.size} items selected"
        }
    }

    private fun changeRecipeStyle(holder: MyTasksHolder, strokeColor: Int) {
        holder.binding.myTaskRowCardView.strokeColor =
            ContextCompat.getColor(requireActivity, strokeColor)
    }

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        APP_ACTIVITY.mToolbar.visibility = View.GONE
        actionMode?.menuInflater?.inflate(R.menu.my_tasks_contextual_menu, menu)
        mActionMode = actionMode!!
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, menu: MenuItem?): Boolean {
        if (menu?.itemId == R.id.delete_my_task_menu) {
            selectedTasks.forEach { mViewModel.deleteMyTask(it) }
            showSnackBar("${selectedTasks.size} task/s removed")
            multiSelection = false
            selectedTasks.clear()
            actionMode?.finish()
        }
        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        myViewHolders.forEach { holder ->
            changeRecipeStyle(holder, R.color.strokeColor)
        }
        multiSelection = false
        selectedTasks.clear()
        applyStatusBarColor(R.color.statusBarColor)
        APP_ACTIVITY.mToolbar.visibility = View.VISIBLE
    }


    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity, color)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG)
            .setAction(requireActivity.resources.getString(R.string.okay)) {}
            .show()
    }

    fun clearContextualActionMode() {
        if (this::mActionMode.isInitialized) {
            mActionMode.finish()
        }
    }
}