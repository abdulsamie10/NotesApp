package com.example.firstnotesapplication.ui.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.firstnotesapplication.Model.Notes
import com.example.firstnotesapplication.R
import com.example.firstnotesapplication.databinding.ItemNotesBinding
import com.example.firstnotesapplication.ui.Fragments.HomeFragmentDirections

class NotesAdapter(val requireContext: Context, val notesList: List<Notes>) :
    RecyclerView.Adapter<NotesAdapter.notesViewHolder>() {

    class notesViewHolder(val binding: ItemNotesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): notesViewHolder {
        return notesViewHolder(ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = notesList.size

    override fun onBindViewHolder(holder: notesViewHolder, position: Int) {
        val note = notesList[position]
        holder.binding.notesTitle.text = note.title
        holder.binding.notesSubTitle.text = note.subtitle
        holder.binding.notesDate.text = note.date

        when(note.priority){
            "1" -> {
                holder.binding.viewPriority.setBackgroundResource(R.drawable.green_dot)
            }
            "2" -> {
                holder.binding.viewPriority.setBackgroundResource(R.drawable.yellow_dot)
            }
            "3" -> {
                holder.binding.viewPriority.setBackgroundResource(R.drawable.red_dot)
            }
        }

        holder.binding.root.setOnClickListener{

            val action = HomeFragmentDirections.actionHomeFragmentToEditNotesFragment(note)
            Navigation.findNavController(it).navigate(action)

        }
    }
}