package com.tribo_mkt.evaluation.repository

import com.tribo_mkt.evaluation.model.AlbumResposta

interface AlbunsRepository {
    suspend fun getPerUser(userId: Int): List<AlbumResposta>
}