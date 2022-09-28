package com.nandaiqbalh.notetaking.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nandaiqbalh.notetaking.data.local.note.NoteDao
import com.nandaiqbalh.notetaking.data.local.note.NoteEntity
import com.nandaiqbalh.notetaking.data.local.user.UserDao
import com.nandaiqbalh.notetaking.data.local.user.UserEntity

@Database(entities = [UserEntity::class, NoteEntity::class], version = 3, exportSchema = false)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract val userDao: UserDao
    abstract val noteDao: NoteDao

    companion object {

        @Volatile
        var INSTANCE: ApplicationDatabase? = null

        fun getInstance(context: Context): ApplicationDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ApplicationDatabase::class.java,
                        "app_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}