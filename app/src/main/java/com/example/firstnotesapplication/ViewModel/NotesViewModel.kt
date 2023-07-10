package com.example.firstnotesapplication.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.firstnotesapplication.Database.NotesDatabase
import com.example.firstnotesapplication.Model.Notes
import com.example.firstnotesapplication.Repository.NotesRepository

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    val repository: NotesRepository

    init{
        val dao = NotesDatabase.getDatabaseInstance(application).myNotesDao()
        repository = NotesRepository(dao)
    }

    fun getNotes():LiveData<List<Notes>> = repository.getAllNotes()

    fun insertNotes(note: Notes){

        repository.insetNotes(note)
    }

    fun deleteNotes(id: Int){
        repository.deleteNotes(id)
    }

    fun updateNotes(note: Notes){
        repository.updateNotes(note)
    }
}