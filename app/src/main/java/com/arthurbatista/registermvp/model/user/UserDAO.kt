package com.arthurbatista.registermvp.model.user

import android.arch.persistence.room.*

@Dao
interface UserDAO {

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun getAllUser(): List<User>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)

    @Delete
    fun delete(user: User)

    @Update
    fun update(user: User)

    @Query("SELECT COUNT(*) FROM user_table")
    fun countUsers(): Long
}