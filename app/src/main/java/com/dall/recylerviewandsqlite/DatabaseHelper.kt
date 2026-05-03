package com.dall.recylerviewandsqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "TaskManager.db"
        private const val TABLE_TASKS = "tasks"

        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_DESC = "description"
        private const val COLUMN_PRIORITY = "priority"
        private const val COLUMN_DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_TASKS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_TITLE TEXT NOT NULL,"
                + "$COLUMN_DESC TEXT,"
                + "$COLUMN_PRIORITY TEXT,"
                + "$COLUMN_DATE TEXT)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TASKS")
        onCreate(db)
    }

    // CREATE: Menyimpan data ke SQLite
    fun insertTask(task: Task): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_TITLE, task.title)
        values.put(COLUMN_DESC, task.description)
        values.put(COLUMN_PRIORITY, task.priority)
        values.put(COLUMN_DATE, task.date)

        val success = db.insert(TABLE_TASKS, null, values)
        db.close()
        return success
    }

    // READ: Mengambil semua data untuk ditampilkan di RecyclerView
    fun getAllTasks(): ArrayList<Task> {
        val taskList = ArrayList<Task>()
        val selectQuery = "SELECT * FROM $TABLE_TASKS ORDER BY $COLUMN_ID DESC"
        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
                val desc = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESC))
                val priority = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))

                val task = Task(id, title, desc, priority, date)
                taskList.add(task)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return taskList
    }
}