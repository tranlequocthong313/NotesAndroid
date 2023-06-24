package com.example.notesandroid.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.notesandroid.R
import com.example.notesandroid.adapters.NotesAdapter
import com.example.notesandroid.databinding.FragmentNotesBinding
import com.example.notesandroid.db.NotesDatabase
import com.example.notesandroid.ui.viewmodels.NotesViewModel
import com.example.notesandroid.ui.viewmodels.NotesViewModelFactory
import com.example.notesandroid.utils.NotesItemTouchHelper
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class NotesFragment : Fragment() {
    private var _binding: FragmentNotesBinding? = null
    private val binding: FragmentNotesBinding get() = _binding!!
    private lateinit var viewModel: NotesViewModel
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        val dao = NotesDatabase.getInstance(requireNotNull(activity).application).notesDao
        viewModel = ViewModelProvider(this, NotesViewModelFactory(dao))[NotesViewModel::class.java]
        notesAdapter = NotesAdapter {
            findNavController().navigate(
                NotesFragmentDirections.actionNotesFragmentToUpsertNotesFragment(
                    it
                )
            )
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvNotes.apply {
            adapter = notesAdapter
        }
        viewModel.notes.observe(viewLifecycleOwner) {
            notesAdapter.submitList(it)
            if (it.isEmpty()) {
                binding.rvNotes.visibility = View.GONE
                binding.llNoData.visibility = View.VISIBLE
            } else {
                binding.rvNotes.visibility = View.VISIBLE
                binding.llNoData.visibility = View.GONE
            }
        }
        binding.fabAddNotes.setOnClickListener {
            findNavController().navigate(R.id.action_notesFragment_to_upsertNotesFragment)
        }
        val itemTouchHelper = NotesItemTouchHelper {
            val note = notesAdapter.currentList[it]
            note.deleted = true
            try {
                viewModel.deleteNote(note)
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG)
            }
        }
        ItemTouchHelper(itemTouchHelper).apply {
            attachToRecyclerView(binding.rvNotes)
        }

        var job: Job? = null
        binding.etSearch.addTextChangedListener {
            job?.cancel()
            job = MainScope().launch {
                delay(150)
                viewModel.searchNotes(it.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}