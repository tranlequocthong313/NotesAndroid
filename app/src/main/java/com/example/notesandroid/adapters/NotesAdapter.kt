package com.example.notesandroid.adapters

import android.graphics.Paint
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notesandroid.databinding.FragmentNotesItemBinding
import com.example.notesandroid.models.Note
import com.example.notesandroid.utils.Converter
import com.example.notesandroid.utils.NotesItemDiffCallback

class NotesAdapter(private val onClick: (Note) -> Unit) :
    ListAdapter<Note, NotesAdapter.NotesItemViewHolder>(NotesItemDiffCallback) {

    class NotesItemViewHolder(private val binding: FragmentNotesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup) = NotesItemViewHolder(
                FragmentNotesItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        fun bind(note: Note, onClick: (Note) -> Unit) {
            binding.apply {
                if (note.title.isBlank()) {
                    tvTitle.text = note.content
                    tvContent.text = "No text"
                } else {
                    tvTitle.text = note.title
                    tvContent.text = note.content.ifBlank { "No text" }
                }
                tvTimeTillNow.text = Converter.convertTimestampToDateTime(note.createdAt)
                root.setOnClickListener { onClick(note) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesItemViewHolder {
        return NotesItemViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: NotesItemViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }
}