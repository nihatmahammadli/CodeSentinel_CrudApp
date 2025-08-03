package com.nihatmahammadli.crudapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val description: String
)
