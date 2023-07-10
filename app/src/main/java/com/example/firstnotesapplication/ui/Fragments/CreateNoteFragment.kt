package com.example.firstnotesapplication.ui.Fragments

import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.firstnotesapplication.MainActivity
import com.example.firstnotesapplication.Model.Notes
import com.example.firstnotesapplication.R
import com.example.firstnotesapplication.ViewModel.NotesViewModel
import com.example.firstnotesapplication.databinding.FragmentCreateNoteBinding
import java.util.Date

class CreateNoteFragment : Fragment() {

    lateinit var binding : FragmentCreateNoteBinding
    var priority: String = "1"
    val noteViewModel : NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentCreateNoteBinding.inflate(layoutInflater,container, false)
        binding.pGreen.setImageResource(R.drawable.baseline_done_24)

        binding.pGreen.setOnClickListener{
            priority = "1"
            binding.pGreen.setImageResource(R.drawable.baseline_done_24)
            binding.pRed.setImageResource(0)
            binding.pYellow.setImageResource(0)
        }

        binding.pRed.setOnClickListener{
            priority = "3"
            binding.pGreen.setImageResource(0)
            binding.pRed.setImageResource(R.drawable.baseline_done_24)
            binding.pYellow.setImageResource(0)
        }

        binding.pYellow.setOnClickListener{
            priority = "2"
            binding.pGreen.setImageResource(0)
            binding.pRed.setImageResource(0)
            binding.pYellow.setImageResource(R.drawable.baseline_done_24)
        }

        binding.btnSaveNotes.setOnClickListener{
            createNotes(it)
        }

        return binding.root
    }

    private fun createNotes(it: View?) {
        val title = binding.editTitle.text.toString()
        val subtitle = binding.editSubtitle.text.toString()
        val notesText = binding.editNotes.text.toString()

        val d = Date()
        val notesDate: CharSequence = DateFormat.format("MMMM d, yyyy", d.time)

        val note = Notes(null,
            title = title,
            subtitle = subtitle,
            notes = notesText,
            date = notesDate.toString(),
            priority)

        noteViewModel.insertNotes(note)

        Toast.makeText(context, "Notes has been added", Toast.LENGTH_SHORT).show()

        Navigation.findNavController(it!!).navigate(R.id.action_createNoteFragment_to_homeFragment)

    }

}