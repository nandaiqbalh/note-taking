package com.nandaiqbalh.notetaking.data.local.user


interface UserDataSource {
    suspend fun registerUser(user: UserEntity): Long

    suspend fun getUser(username: String) : UserEntity
}

class UserDataSourceImpl(private val userDao: UserDao): UserDataSource {
    override suspend fun registerUser(user: UserEntity): Long {
        return userDao.registerUser(user)
    }

    override suspend fun getUser(username: String): UserEntity {
        return userDao.getUser(username)
    }
}