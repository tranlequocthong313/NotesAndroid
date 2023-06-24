package com.example.notesandroid.models

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var id: Long = 0L,
    @ColumnInfo
    var title: String = "",
    @ColumnInfo
    var content: String = "",
    @ColumnInfo
    val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo
    var deleted: Boolean = false
) : Serializable
