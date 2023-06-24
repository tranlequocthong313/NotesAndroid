package com.example.notesandroid.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesandroid.db.NotesDao
import com.example.notesandroid.models.Note
import kotlinx.coroutines.launch

class NotesViewModel(private val notesDao: NotesDao) : ViewModel() {
    private val _notes = MediatorLiveData<List<Note>>()
    val notes: LiveData<List<Note>> get() = _notes

    init {
        _notes.addSource(notesDao.getAllNotes()) { _notes.value = it }
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        notesDao.deleteNote(note)
    }

    fun searchNotes(search: String) {
        val searchedNotes = notesDao.searchNotes("%$search%")
        _notes.removeSource(notesDao.getAllNotes())
        _notes.addSource(searchedNotes) {
            _notes.value = it
        }
    }
}