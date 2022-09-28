package com.nandaiqbalh.notetaking.data.local.note

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    var noteId: Long = 0L,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "description")
    var description: String? = null
) : Parcelable