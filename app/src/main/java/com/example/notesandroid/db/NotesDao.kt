package com.example.notesandroid.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.notesandroid.models.Note

@Dao
interface NotesDao {
    @Insert
    suspend fun insert(note: Note): Long

    @Update
    suspend fun update(note: Note)

    // Soft delete by only updating delete field in notes table
    @Update
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM notes WHERE deleted = :deleted ORDER BY id DESC")
    fun getAllNotes(deleted: Boolean = false): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :id AND deleted = :deleted")
    fun getNote(id: Long, deleted: Boolean = false): LiveData<Note>

    @Query("SELECT * FROM notes WHERE deleted = :deleted AND (title LIKE :search OR content LIKE :search) ORDER BY id DESC")
    fun searchNotes(search: String, deleted: Boolean = false): LiveData<List<Note>>
}