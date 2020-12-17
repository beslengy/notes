package com.molchanov.notes.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity (tableName = "notes_tables")
data class AppNote (

        //Генерим id для ROOM
        @PrimaryKey (autoGenerate = true) val id : Int = 0,

        @ColumnInfo val name: String = "",
        @ColumnInfo val text: String = "",

        //Здесь id для Firebase
        val idFirebase : String = ""

        ) : Serializable