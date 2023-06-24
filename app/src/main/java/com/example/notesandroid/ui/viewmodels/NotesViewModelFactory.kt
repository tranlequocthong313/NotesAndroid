package com.example.notesandroid.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesandroid.db.NotesDao
import java.lang.IllegalArgumentException

class NotesViewModelFactory(private val dao: NotesDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            return NotesViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}