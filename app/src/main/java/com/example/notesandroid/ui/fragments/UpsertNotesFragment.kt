package com.example.notesandroid.ui.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.notesandroid.databinding.FragmentUpsertNotesBinding
import com.example.notesandroid.db.NotesDatabase
import com.example.notesandroid.models.Note
import com.example.notesandroid.ui.viewmodels.UpsertNotesViewModel
import com.example.notesandroid.ui.viewmodels.UpsertNotesViewModelFactory
import com.example.notesandroid.utils.Converter
import java.lang.Exception

class UpsertNotesFragment : Fragment() {
    private var _binding: FragmentUpsertNotesBinding? = null
    private val binding: FragmentUpsertNotesBinding get() = _binding!!
    private lateinit var viewModel: UpsertNotesViewModel
    private var note: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpsertNotesBinding.inflate(inflater, container, false)
        val dao = NotesDatabase.getInstance(requireNotNull(activity).application).notesDao
        viewModel = ViewModelProvider(
            this,
            UpsertNotesViewModelFactory(dao)
        )[UpsertNotesViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            etContent.addTextChangedListener {
                binding.tvCharsCount.text = "${etContent.text.count()} characters"
            }
        }
        note = UpsertNotesFragmentArgs.fromBundle(requireArguments()).note
        note?.let {
            binding.etTitle.setText(it.title)
            binding.etContent.setText(it.content)
            binding.tvCharsCount.text = "${it.content.count()} characters"
            binding.tvDateTime.text = Converter.convertTimestampToDateTime(it.createdAt)
            return
        }
        binding.tvDateTime.text = Converter.convertTimestampToDateTime(System.currentTimeMillis())
    }

    override fun onStop() {
        super.onStop()
        handleUpsert()
    }

    private fun handleUpsert() {
        binding.apply {
            try {
                note?.let {
                    if (isDeleting()) {
                        it.deleted = true
                        viewModel.deleteNote(it)
                        return
                    }
                    it.title = etTitle.text.toString()
                    it.content = etContent.text.toString()
                    viewModel.updateNote(it)
                    return
                }
                if (canCreateNewNote()) {
                    val note = Note()
                    note.title = etTitle.text.toString()
                    note.content = etContent.text.toString()
                    viewModel.insertNote(note)
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG)
            }
        }
    }

    private fun isDeleting() = binding.etTitle.text.isBlank() and binding.etContent.text.isBlank()

    private fun canCreateNewNote() =
        binding.etTitle.text.isNotBlank() or binding.etContent.text.isNotBlank()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}