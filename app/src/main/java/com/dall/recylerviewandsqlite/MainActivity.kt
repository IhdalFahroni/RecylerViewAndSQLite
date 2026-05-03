package com.dall.recylerviewandsqlite

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var tvTaskCount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)
        recyclerView = findViewById(R.id.rvTasks)
        tvTaskCount = findViewById(R.id.tvTaskCount)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)
        fabAdd.setOnClickListener {
            startActivity(Intent(this, AddTaskActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        // Muat ulang data setiap kali kembali ke halaman ini
        loadTasks()
    }

    private fun loadTasks() {
        val taskList = dbHelper.getAllTasks()
        taskAdapter = TaskAdapter(taskList)
        recyclerView.adapter = taskAdapter
        tvTaskCount.text = "${taskList.size} tasks pending"
    }
}