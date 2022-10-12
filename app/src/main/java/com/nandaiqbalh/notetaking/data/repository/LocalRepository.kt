package com.nandaiqbalh.notetaking.data.repository

import com.nandaiqbalh.notetaking.data.local.note.NoteDataSource
import com.nandaiqbalh.notetaking.data.local.note.NoteEntity
import com.nandaiqbalh.notetaking.data.local.user.UserDataSource
import com.nandaiqbalh.notetaking.data.local.user.UserEntity
import com.nandaiqbalh.notetaking.wrapper.Resource

interface LocalRepository {
    suspend fun checkIfNoteListIsEmpty(): Boolean

    suspend fun registerUser(user: UserEntity): Resource<Number>

    suspend fun getUser(username: String): Resource<UserEntity>

    suspend fun insertNote(note: NoteEntity): Resource<Number>

    suspend fun updateNote(note: NoteEntity): Resource<Number>

    suspend fun deleteNote(note: NoteEntity): Resource<Number>

    suspend fun getNoteList(): Resource<List<NoteEntity>>

    suspend fun getNoteById(id: Long) : Resource<NoteEntity>
}

class LocalRepositoryImpl(
    private val userDataSource: UserDataSource,
    private val noteDataSource: NoteDataSource,
): LocalRepository {

    override suspend fun checkIfNoteListIsEmpty(): Boolean {
        return noteDataSource.getNoteList().isEmpty()
    }

    override suspend fun registerUser(user: UserEntity): Resource<Number> {
        return proceed {
            userDataSource.registerUser(user)
        }
    }

    override suspend fun getUser(username: String): Resource<UserEntity> {
        return proceed {
            userDataSource.getUser(username)
        }
    }

    override suspend fun insertNote(note: NoteEntity): Resource<Number> {
        return proceed {
            noteDataSource.insertNote(note)
        }
    }

    override suspend fun updateNote(note: NoteEntity): Resource<Number> {
        return proceed {
            noteDataSource.updateNote(note)
        }
    }

    override suspend fun deleteNote(note: NoteEntity): Resource<Number> {
        return proceed {
            noteDataSource.deleteNote(note)
        }
    }

    override suspend fun getNoteList(): Resource<List<NoteEntity>> {
        return proceed {
            noteDataSource.getNoteList()
        }
    }

    override suspend fun getNoteById(id: Long): Resource<NoteEntity> {
        return proceed {
            noteDataSource.getNoteById(id)
        }
    }

    private suspend fun <T> proceed(coroutine: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutine.invoke())
        } catch (exception: Exception) {
            Resource.Error(exception)
        }
    }

}