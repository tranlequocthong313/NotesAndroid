package com.example.notesandroid.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesandroid.db.NotesDao

class UpsertNotesViewModelFactory(private val dao: NotesDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpsertNotesViewModel::class.java)) {
            return UpsertNotesViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}