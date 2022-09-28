package com.nandaiqbalh.notetaking.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nandaiqbalh.notetaking.data.local.note.NoteEntity
import com.nandaiqbalh.notetaking.data.repository.LocalRepository
import com.nandaiqbalh.notetaking.wrapper.Resource
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: LocalRepository): ViewModel(){

    private val _insertResult = MutableLiveData<Resource<Number>>()
    val insertResult: LiveData<Resource<Number>> get() = _insertResult

    private val _updateResult = MutableLiveData<Resource<Number>>()
    val updateResult: LiveData<Resource<Number>> get() = _updateResult

    private val _deleteResult = MutableLiveData<Resource<Number>>()
    val deleteResult: LiveData<Resource<Number>> get() = _deleteResult

    private val _getNoteResult = MutableLiveData<Resource<NoteEntity>>()
    val getNoteResult: LiveData<Resource<NoteEntity>> get() = _getNoteResult

    private val _getNoteListResult = MutableLiveData<Resource<List<NoteEntity>>>()
    val getNoteListResult: LiveData<Resource<List<NoteEntity>>> get() = _getNoteListResult

    fun insertNote(note: NoteEntity) {
        viewModelScope.launch {
            _insertResult.postValue(repository.insertNote(note))
        }
    }

    fun updateNote(note: NoteEntity) {
        viewModelScope.launch {
            _updateResult.postValue(repository.updateNote(note))
        }
    }

    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch {
            _deleteResult.postValue(repository.deleteNote(note))
        }
    }

    fun getNoteById(id: Long) {
        viewModelScope.launch {
            _getNoteResult.postValue(repository.getNoteById(id))
        }
    }

    fun getNoteList() {
        viewModelScope.launch {
            _getNoteListResult.postValue((repository.getNoteList()))
        }
    }
}