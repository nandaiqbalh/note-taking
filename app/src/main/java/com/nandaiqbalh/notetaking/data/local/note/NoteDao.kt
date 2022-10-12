package com.nandaiqbalh.notetaking.data.local.note

import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    suspend fun insertNote(note: NoteEntity): Long

    @Update
    suspend fun updateNote(note: NoteEntity): Int

    @Delete
    suspend fun deleteNote(note: NoteEntity): Int

    @Query("SELECT * FROM notes ORDER BY noteId DESC")
    suspend fun getNoteList(): List<NoteEntity>

    @Query("SELECT * FROM notes WHERE noteId = :id")
    suspend fun getNoteById(id: Long) : NoteEntity

}