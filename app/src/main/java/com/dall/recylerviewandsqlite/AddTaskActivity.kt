package com.dall.recylerviewandsqlite

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddTaskActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        dbHelper = DatabaseHelper(this)

        val etTitle = findViewById<EditText>(R.id.etTaskTitle)
        val etDesc = findViewById<EditText>(R.id.etTaskDesc)
        val spinnerPriority = findViewById<Spinner>(R.id.spinnerPriority)
        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val desc = etDesc.text.toString().trim()
            val priority = spinnerPriority.selectedItem.toString()

            // Validasi: Input tidak boleh kosong
            if (title.isEmpty()) {
                etTitle.error = "Title cannot be empty"
                return@setOnClickListener
            }
            if (desc.isEmpty()) {
                etDesc.error = "Description cannot be empty"
                return@setOnClickListener
            }

            // Generate otomatis tanggal pembuatan tugas
            val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            val currentDate = sdf.format(Date())

            val task = Task(title = title, description = desc, priority = priority, date = currentDate)
            val result = dbHelper.insertTask(task)

            if (result > -1) {
                Toast.makeText(this, "Task Saved Successfully!", Toast.LENGTH_SHORT).show()
                finish() // Menutup halaman input & kembali ke MainActivity
            } else {
                Toast.makeText(this, "Failed to save task", Toast.LENGTH_SHORT).show()
            }
        }
    }
}