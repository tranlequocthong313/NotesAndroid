package com.example.notesandroid.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesandroid.db.NotesDao
import com.example.notesandroid.models.Note
import kotlinx.coroutines.launch

class UpsertNotesViewModel(private val dao: NotesDao) : ViewModel() {
    fun insertNote(note: Note) {
        viewModelScope.launch {
            dao.insert(trim(note))
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            dao.update(trim(note))
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            dao.deleteNote(trim(note))
        }
    }

    private fun trim(note: Note): Note {
        note.title = note.title.trim()
        note.content = note.content.trim()
        return note
    }
}