package com.example.firstnotesapplication.ui.Fragments

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.firstnotesapplication.R
import com.example.firstnotesapplication.ViewModel.NotesViewModel
import com.example.firstnotesapplication.databinding.FragmentHomeBinding
import com.example.firstnotesapplication.ui.Adapter.NotesAdapter

class HomeFragment : Fragment() {

    lateinit var binding : FragmentHomeBinding
    val noteViewModel : NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        noteViewModel.getNotes().observe(viewLifecycleOwner, { notesList ->
            binding.rcvAllNotes.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.rcvAllNotes.adapter = NotesAdapter(requireContext(), notesList)

            }
        )


        binding.btnAddNotes.setOnClickListener{

            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_createNoteFragment)

        }

        return binding.root
    }

}