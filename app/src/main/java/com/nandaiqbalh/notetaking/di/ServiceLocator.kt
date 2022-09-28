package com.nandaiqbalh.notetaking.di

import android.content.Context
import com.nandaiqbalh.notetaking.data.local.ApplicationDatabase
import com.nandaiqbalh.notetaking.data.local.note.NoteDao
import com.nandaiqbalh.notetaking.data.local.note.NoteDataSource
import com.nandaiqbalh.notetaking.data.local.note.NoteDataSourceImpl
import com.nandaiqbalh.notetaking.data.local.user.UserDao
import com.nandaiqbalh.notetaking.data.local.user.UserDataSource
import com.nandaiqbalh.notetaking.data.local.user.UserDataSourceImpl
import com.nandaiqbalh.notetaking.data.repository.LocalRepository
import com.nandaiqbalh.notetaking.data.repository.LocalRepositoryImpl

object ServiceLocator {

    fun provideAppDatabase(context: Context): ApplicationDatabase {
        return ApplicationDatabase.getInstance(context)
    }

    fun provideUserDao(context: Context): UserDao {
        return provideAppDatabase(context).userDao
    }

    fun provideUserDataSource(context: Context): UserDataSource {
        return UserDataSourceImpl(provideUserDao(context))
    }

    fun provideNoteDao(context: Context): NoteDao {
        return provideAppDatabase(context).noteDao
    }

    fun provideNoteDataSource(context: Context): NoteDataSource {
        return NoteDataSourceImpl(provideNoteDao(context))
    }

    fun provideServiceLocator(context: Context): LocalRepository {
        return LocalRepositoryImpl(
            provideUserDataSource(context),
            provideNoteDataSource(context)
        )
    }
}