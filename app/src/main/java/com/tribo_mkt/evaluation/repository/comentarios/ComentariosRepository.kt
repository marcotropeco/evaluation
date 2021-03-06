package com.tribo_mkt.evaluation.repository.comentarios

import com.tribo_mkt.evaluation.model.ComentarioResposta

interface ComentariosRepository {
    suspend fun getPerUser(userId: Int): List<ComentarioResposta>
    suspend fun getPerPost(postId: Int): List<ComentarioResposta>
}