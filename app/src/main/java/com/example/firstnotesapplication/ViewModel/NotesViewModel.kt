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

    fun getHighNotes():LiveData<List<Notes>> = repository.getHighNotes()

    fun getMediumNotes():LiveData<List<Notes>> = repository.getMediumNotes()

    fun getLowNotes():LiveData<List<Notes>> = repository.getLowNotes()

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