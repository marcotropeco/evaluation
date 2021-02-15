package com.tribo_mkt.evaluation.repository

import com.tribo_mkt.evaluation.model.PostagemResposta

interface PostagensRepository {
    suspend fun getPerUser(userId: Int): List<PostagemResposta>
}