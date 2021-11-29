package com.yoenas.githubusers.data.repository

import android.app.Application
import com.yoenas.githubusers.data.db.FavoriteDB
import com.yoenas.githubusers.data.db.FavoriteDao
import com.yoenas.githubusers.data.model.DetailUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class FavoriteRepository(application: Application) {
    private val favoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteDB.getDatabase(application)
        favoriteDao = db.favoriteDao()
    }

    fun getFavoriteUser(): Flow<List<DetailUser>> {
        return flow {
            val favoriteUser = favoriteDao.getFavorites()
            emit(favoriteUser)
        }.flowOn(Dispatchers.IO)
    }

    fun insert(favoriteUser: DetailUser) {
        executorService.execute { favoriteDao.insert(favoriteUser) }
    }

    fun delete(favoriteUser: DetailUser) {
        executorService.execute { favoriteDao.delete(favoriteUser) }
    }

}