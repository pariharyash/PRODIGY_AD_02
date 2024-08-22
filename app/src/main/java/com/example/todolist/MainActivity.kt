package com.example.todolist

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.ui.theme.ToDoListTheme

class MainActivity : ComponentActivity() {
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskList: MutableList<Task>
    private lateinit var etTask: EditText
    private lateinit var btnAdd: Button
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskList = mutableListOf()

        etTask = findViewById(R.id.etTask)
        btnAdd = findViewById(R.id.btnAdd)
        recyclerView = findViewById(R.id.recyclerView)

        taskAdapter = TaskAdapter(taskList, { task, position -> editTask(task, position) }, { position -> deleteTask(position) })
        recyclerView.adapter = taskAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        btnAdd.setOnClickListener {
            val taskTitle = etTask.text.toString()
            if (taskTitle.isNotEmpty()) {
                val newTask = Task(taskTitle)
                taskList.add(newTask)
                taskAdapter.notifyItemInserted(taskList.size - 1)
                etTask.text.clear()
            }
        }
}

    private fun editTask(task: Task, position: Int) {
        val editTaskEditText = EditText(this)
        editTaskEditText.setText(task.title)

        AlertDialog.Builder(this)
            .setTitle("Edit Task")
            .setView(editTaskEditText)
            .setPositiveButton("Update") { dialog, _ ->
                val updatedTaskTitle = editTaskEditText.text.toString()
                if (updatedTaskTitle.isNotEmpty()) {
                    task.title = updatedTaskTitle
                    taskAdapter.notifyItemChanged(position)
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .create()
            .show()
    }

    private fun deleteTask(position: Int) {
        taskList.removeAt(position)
        taskAdapter.notifyItemRemoved(position)
    }
}