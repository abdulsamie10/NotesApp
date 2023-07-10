package com.example.firstnotesapplication.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.firstnotesapplication.Model.Notes

@Dao
interface NotesDao {

    @Query("SELECT * from Notes")
    fun getNotes():LiveData<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(notes: Notes)

    @Query("DELETE from Notes where id=:id")
    fun deleteNotes(id : Int)

    @Update
    fun updateNotes(notes: Notes)

}