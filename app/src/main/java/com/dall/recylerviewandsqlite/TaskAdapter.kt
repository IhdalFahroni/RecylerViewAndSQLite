package com.dall.recylerviewandsqlite

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private val taskList: ArrayList<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewPriorityIndicator: View = itemView.findViewById(R.id.viewPriorityIndicator)
        val tvPriorityBadge: TextView = itemView.findViewById(R.id.tvPriorityBadge)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]

        holder.tvTitle.text = task.title
        holder.tvDescription.text = task.description
        holder.tvDate.text = task.date
        holder.tvPriorityBadge.text = task.priority

        // Pewarnaan dinamis berdasarkan Prioritas
        when (task.priority.lowercase()) {
            "high" -> {
                holder.viewPriorityIndicator.setBackgroundColor(Color.parseColor("#ba1a1a")) // Error Red
                holder.tvPriorityBadge.setTextColor(Color.parseColor("#ba1a1a"))
            }
            "medium" -> {
                holder.viewPriorityIndicator.setBackgroundColor(Color.parseColor("#006a60")) // Secondary Green
                holder.tvPriorityBadge.setTextColor(Color.parseColor("#006a60"))
            }
            else -> {
                holder.viewPriorityIndicator.setBackgroundColor(Color.parseColor("#757684")) // Outline Grey
                holder.tvPriorityBadge.setTextColor(Color.parseColor("#757684"))
            }
        }
    }

    override fun getItemCount(): Int = taskList.size
}