package com.example.firstnotesapplication.ui.Fragments

import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.firstnotesapplication.Model.Notes
import com.example.firstnotesapplication.R
import com.example.firstnotesapplication.ViewModel.NotesViewModel
import com.example.firstnotesapplication.databinding.FragmentEditNotesBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Date

class EditNotesFragment : Fragment() {

    val notes by navArgs<EditNotesFragmentArgs>()
    lateinit var binding: FragmentEditNotesBinding
    var priority: String = "1"
    val noteViewModel : NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentEditNotesBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)

        binding.editTitle.setText(notes.data.title)
        binding.editSubtitle.setText(notes.data.subtitle)
        binding.editNotes.setText(notes.data.notes)

        when(notes.data.priority){
            "1" -> {
                priority = "1"
                binding.pGreen.setImageResource(R.drawable.baseline_done_24)
                binding.pRed.setImageResource(0)
                binding.pYellow.setImageResource(0)
            }
            "2" -> {
                priority = "2"
                binding.pGreen.setImageResource(0)
                binding.pRed.setImageResource(0)
                binding.pYellow.setImageResource(R.drawable.baseline_done_24)
            }
            "3" -> {
                priority = "3"
                binding.pGreen.setImageResource(0)
                binding.pRed.setImageResource(R.drawable.baseline_done_24)
                binding.pYellow.setImageResource(0)
            }
        }

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


        binding.btnEditSaveNotes.setOnClickListener{
            updateNotes(it)
        }

        return binding.root
    }

    private fun updateNotes(it: View?) {
        val title = binding.editTitle.text.toString()
        val subtitle = binding.editSubtitle.text.toString()
        val notesText = binding.editNotes.text.toString()

        val d = Date()
        val notesDate: CharSequence = DateFormat.format("MMMM d, yyyy", d.time)

        val note = Notes(notes.data.id,
            title = title,
            subtitle = subtitle,
            notes = notesText,
            date = notesDate.toString(),
            priority)

        noteViewModel.updateNotes(note)

        Toast.makeText(context, "Notes has been added", Toast.LENGTH_SHORT).show()

        Navigation.findNavController(it!!).navigate(R.id.action_editNotesFragment_to_homeFragment2)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.menu_delete){

            val bottomSheet: BottomSheetDialog = BottomSheetDialog(requireContext())
            bottomSheet.setContentView(R.layout.dialog_delete)

            val textViewYes = bottomSheet.findViewById<TextView>(R.id.dialog_yes)
            val textViewNo = bottomSheet.findViewById<TextView>(R.id.dialog_no)

            textViewYes?.setOnClickListener{
                bottomSheet.dismiss()
                noteViewModel.deleteNotes(notes.data.id!!)

            }

            textViewNo?.setOnClickListener{
                bottomSheet.dismiss()
            }

            bottomSheet.show()
        }

        return super.onOptionsItemSelected(item)
    }

}