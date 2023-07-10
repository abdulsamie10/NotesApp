package com.example.firstnotesapplication.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.firstnotesapplication.Dao.NotesDao
import com.example.firstnotesapplication.Model.Notes

class NotesRepository(val dao: NotesDao) {

    fun getAllNotes():LiveData<List<Notes>>{
        return dao.getNotes()
    }

    fun getHighNotes():LiveData<List<Notes>> = dao.getHighNotes()

    fun getMediumNotes():LiveData<List<Notes>> = dao.getMediumNotes()

    fun getLowNotes():LiveData<List<Notes>> = dao.getLowNotes()

    fun insetNotes(notes: Notes){
        dao.insertNotes(notes)
    }

    fun deleteNotes(id: Int){
        dao.deleteNotes(id)
    }

    fun updateNotes(notes: Notes){
        dao.updateNotes(notes)
    }

}