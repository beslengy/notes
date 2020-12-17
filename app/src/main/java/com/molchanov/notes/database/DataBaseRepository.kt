package com.molchanov.notes.database

import androidx.lifecycle.LiveData
import com.molchanov.notes.model.AppNote

//Интерфейс для работы с базой данных
interface DataBaseRepository {
    //переменная хранит лайвдату со списком всех заметок
    val allNotes: LiveData<List<AppNote>>
    //функция для добавления заметки в базу данных
    //suspend - функция выполняется в отдельной корутине, может быть приостановлена
    suspend fun insert (note: AppNote, onSuccess: () -> Unit)
    suspend fun delete (note: AppNote, onSuccess: () -> Unit)

    fun connectToDatabase(onSuccess: () -> Unit, onFail: (String) -> Unit) {}

    fun signOut() {}
}