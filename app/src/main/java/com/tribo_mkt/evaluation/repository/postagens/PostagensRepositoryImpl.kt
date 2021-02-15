package com.tribo_mkt.evaluation.repository.postagens

import com.tribo_mkt.evaluation.model.PostagemResposta
import com.tribo_mkt.evaluation.network.RetrofitBuilder

class PostagensRepositoryImpl(private val retrofitService: RetrofitBuilder) : PostagensRepository {
    override suspend fun getPerUser(userId: Int): List<PostagemResposta> = retrofitService.apiService.getPostagensUsuario(userId)
}