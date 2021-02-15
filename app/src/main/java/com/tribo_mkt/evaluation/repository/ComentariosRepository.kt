package com.tribo_mkt.evaluation.repository

import com.tribo_mkt.evaluation.model.ComentarioResposta

interface ComentariosRepository {
    suspend fun getPerUser(userId: Int): List<ComentarioResposta>
}