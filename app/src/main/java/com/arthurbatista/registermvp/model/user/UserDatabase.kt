package com.arthurbatista.registermvp.model.user

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room.databaseBuilder
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = arrayOf(User::class) , version = 1)
abstract class UserDatabase : RoomDatabase(){

    abstract fun getUserDao(): UserDAO

    companion object {
        val databaseName = "users_db"
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase? {
            if(INSTANCE == null) {

                synchronized(UserDatabase::class) {
                    INSTANCE = databaseBuilder(
                        context.getApplicationContext(),
                        UserDatabase::class.java,
                        databaseName
                    ).build()
                }
            }
            return INSTANCE
        }
    }

}