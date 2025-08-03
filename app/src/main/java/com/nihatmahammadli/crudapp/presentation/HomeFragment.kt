package com.nihatmahammadli.crudapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nihatmahammadli.crudapp.data.Task
import com.nihatmahammadli.crudapp.databinding.ItemTaskAdapterBinding

class TaskAdapter(
    val onDeleteClick: (Task) -> Unit,
    val onUpdateClick: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.binding.taskTxt.text = task.description

        holder.binding.deleteBtn.setOnClickListener {
            onDeleteClick(task)
        }
        holder.binding.updateBtn.setOnClickListener {
            onUpdateClick(task)
        }
    }

    inner class TaskViewHolder(val binding: ItemTaskAdapterBinding) : RecyclerView.ViewHolder(binding.root)

    class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
    }
}
