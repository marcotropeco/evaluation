package com.tribo_mkt.evaluation.repository.usuarios

import com.tribo_mkt.evaluation.model.UsuarioResposta
import com.tribo_mkt.evaluation.network.RetrofitBuilder

class UsuariosRepositoryImpl (private val retrofitService: RetrofitBuilder): UsuariosRepository {
    override suspend fun get(): List<UsuarioResposta> = retrofitService.apiService.getUsuarios()
}