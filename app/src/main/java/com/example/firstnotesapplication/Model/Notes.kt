package com.example.firstnotesapplication.Model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize
import androidx.room.PrimaryKey

@Parcelize
@Entity(tableName = "Notes")
class Notes (
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null,
    var title: String,
    var subtitle: String,
    var notes: String,
    var date: String,
    var priority: String
): Parcelable