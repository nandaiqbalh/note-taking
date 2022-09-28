package com.nandaiqbalh.notetaking.data.local.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    suspend fun registerUser(user: UserEntity): Long

    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun getUser(username: String) : UserEntity
}