package com.molchanov.notes.database.room

import androidx.lifecycle.LiveData
import com.molchanov.notes.database.DataBaseRepository
import com.molchanov.notes.model.AppNote

class AppRoomRepository (private val appRoomDao: AppRoomDao) : DataBaseRepository {

    override val allNotes: LiveData<List<AppNote>>
        get() = appRoomDao.getAllNotes()

    override suspend fun insert(note: AppNote, onSuccess: () -> Unit) {
        appRoomDao.insert(note)
        onSuccess()
    }

    override suspend fun delete(note: AppNote, onSuccess: () -> Unit) {
        appRoomDao.delete(note)
        onSuccess()

    }

    //Здесь функция реализована и пустая, чтобы не было краша приложения при нажатии на
    //кнопку выхода, когда используешь Room
    override fun signOut() {

    }
}