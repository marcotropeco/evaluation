package com.tribo_mkt.evaluation.repository.fotos

import com.tribo_mkt.evaluation.model.FotoResposta
import com.tribo_mkt.evaluation.network.RetrofitBuilder

class FotosRepositoryImpl(private val retrofitService: RetrofitBuilder) : FotosRepository {
    override suspend fun getPerAlbum(albumId: Int): List<FotoResposta> = retrofitService.apiService.getFotosAlbuns(albumId)
}