package com.tribo_mkt.evaluation.repository

import com.tribo_mkt.evaluation.model.ComentarioResposta
import com.tribo_mkt.evaluation.network.RetrofitBuilder

class ComentariosRepositoryImpl(private val retrofitService: RetrofitBuilder) : ComentariosRepository {
    override suspend fun getPerUser(userId: Int): List<ComentarioResposta> = retrofitService.apiService.getComentariosUsuario(userId)
    override suspend fun getPerPost(postId: Int): List<ComentarioResposta> = retrofitService.apiService.getComentariosPostagens(postId)
}