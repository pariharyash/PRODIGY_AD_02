package com.example.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private var taskList: MutableList<Task>, private val onEdit: (Task, Int) -> Unit, private val onDelete: (Int) -> Unit) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTask: TextView = itemView.findViewById(R.id.tvTask)
        val btnEdit: ImageButton = itemView.findViewById(R.id.btnEdit)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = taskList[position]
        holder.tvTask.text = currentTask.title

        holder.btnEdit.setOnClickListener {
            onEdit(currentTask, position)
        }

        holder.btnDelete.setOnClickListener {
            onDelete(position)
        }
    }

    override fun getItemCount() = taskList.size
}