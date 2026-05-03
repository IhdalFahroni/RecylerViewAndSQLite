package com.dall.recylerviewandsqlite

data class Task(
    var id: Int = 0,
    var title: String,
    var description: String,
    var priority: String,
    var date: String
)