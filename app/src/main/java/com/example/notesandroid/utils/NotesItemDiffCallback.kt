package com.example.notesandroid.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.notesandroid.models.Note

object NotesItemDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Note, newItem: Note) = oldItem == newItem
}