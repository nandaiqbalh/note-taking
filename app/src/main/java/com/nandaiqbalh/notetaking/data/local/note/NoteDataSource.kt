package com.nandaiqbalh.notetaking.data.local.note

interface NoteDataSource {
    suspend fun insertNote(note: NoteEntity): Long

    suspend fun updateNote(note: NoteEntity): Int

    suspend fun deleteNote(note: NoteEntity): Int

    suspend fun getNoteList(): List<NoteEntity>

    suspend fun getNoteById(id: Long) : NoteEntity
}

class NoteDataSourceImpl(private val noteDao: NoteDao): NoteDataSource {
    override suspend fun insertNote(note: NoteEntity): Long {
        return noteDao.insertNote(note)
    }

    override suspend fun updateNote(note: NoteEntity): Int {
        return noteDao.updateNote(note)
    }

    override suspend fun deleteNote(note: NoteEntity): Int {
        return noteDao.deleteNote(note)
    }

    override suspend fun getNoteList(): List<NoteEntity> {
        return noteDao.getNoteList()
    }

    override suspend fun getNoteById(id: Long): NoteEntity {
        return noteDao.getNoteById(id)
    }

}