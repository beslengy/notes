package com.molchanov.notes.utils

import android.content.Context
import android.content.SharedPreferences

object AppPreference {
    //Создаем константы для удобства. Поля содержат названия полей в файле настроек
    private const val  INIT_USER = "initUser"
    private const val TYPE_DB = "typeDB"
    private const val NAME_PREF = "preference"

    //Создаем объект SharedPreferences, который позволяет создать файл настроек во внутренней
    //директории приложения
    private lateinit var mPreferences: SharedPreferences


    //Функция для получения настроек из контекста. Принимает контекст и возвращает SharedPreferences
    fun getPreference(context: Context) : SharedPreferences {
        //В аргументы передаем имя файла и режим хранения
        mPreferences = context.getSharedPreferences(NAME_PREF, Context.MODE_PRIVATE)
        return mPreferences
    }

    //В этой функции задаем значение был ли авторизован пользователь или нет
    fun setInitUser(init : Boolean) {
        mPreferences.edit()
            .putBoolean(INIT_USER, init)
            .apply()
    }

    fun setTypeDB(type : String) {
        mPreferences.edit()
            .putString(TYPE_DB, type)
            .apply()
    }

    fun getInitUser() : Boolean {
        return mPreferences.getBoolean(INIT_USER, false)
    }

    fun getTypeDB () : String {
        return mPreferences.getString(TYPE_DB, TYPE_ROOM).toString()
    }
}