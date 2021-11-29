package com.yoenas.githubusers.data.db

import androidx.room.*
import com.yoenas.githubusers.data.model.DetailUser

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(detailUser: DetailUser)

    @Delete
    fun delete(detailUser: DetailUser)

    @Query("SELECT * from detailUser")
    fun getFavorites(): List<DetailUser>
}