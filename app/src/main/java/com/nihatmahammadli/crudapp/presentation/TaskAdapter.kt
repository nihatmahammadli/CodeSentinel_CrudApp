package com.nihatmahammadli.crudapp.presentation

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nihatmahammadli.crudapp.data.DatabaseProvider
import com.nihatmahammadli.crudapp.data.Task
import com.nihatmahammadli.crudapp.data.TaskDao
import com.nihatmahammadli.crudapp.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var taskDao: TaskDao
    private lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        taskDao = DatabaseProvider.getDatabase(requireContext()).taskDao()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TaskAdapter(
            onDeleteClick = { task -> deleteTask(task) },
            onUpdateClick = { task -> updateTaskDialog(task) }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        loadTasks()

        binding.addNewTask.setOnClickListener {
            addNewTask()
        }
    }

    private fun loadTasks() {
        lifecycleScope.launch {
            val tasks = taskDao.getTasks()
            adapter.submitList(tasks)
        }
    }

    private fun addNewTask() {
        val editText = EditText(requireContext()).apply {
            hint = "Write new task"
            inputType = InputType.TYPE_CLASS_TEXT
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Add New Task")
            .setView(editText)
            .setPositiveButton("Add") { dialog, _ ->
                val taskDesc = editText.text.toString().trim()
                if (taskDesc.isNotEmpty()) {
                    addTaskToDb(taskDesc)
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun addTaskToDb(description: String) {
        val newTask = Task(description = description)
        lifecycleScope.launch {
            taskDao.insertTask(newTask)
            loadTasks()
        }
    }

    private fun deleteTask(task: Task) {
        lifecycleScope.launch {
            taskDao.deleteTask(task)
            loadTasks()
        }
    }

    private fun updateTaskDialog(task: Task) {
        val editText = EditText(requireContext()).apply {
            hint = "Update task"
            setText(task.description)
            inputType = InputType.TYPE_CLASS_TEXT
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Update Task")
            .setView(editText)
            .setPositiveButton("Update") { dialog, _ ->
                val updatedDesc = editText.text.toString().trim()
                if (updatedDesc.isNotEmpty()) {
                    val updatedTask = task.copy(description = updatedDesc)
                    lifecycleScope.launch {
                        taskDao.updateTask(updatedTask)
                        loadTasks()
                    }
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
