package com.tribo_mkt.evaluation.repository

import com.tribo_mkt.evaluation.model.FotoResposta

interface FotosRepository {
    suspend fun getPerAlbum(albumId: Int): List<FotoResposta>
}