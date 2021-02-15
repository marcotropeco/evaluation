package com.tribo_mkt.evaluation.repository.albuns

import com.tribo_mkt.evaluation.model.AlbumResposta
import com.tribo_mkt.evaluation.network.RetrofitBuilder

class AlbunsRepositoryImpl(private val retrofitService: RetrofitBuilder) : AlbunsRepository {
    override suspend fun getPerUser(userId: Int): List<AlbumResposta> = retrofitService.apiService.getAlbunsUsuario(userId)
}