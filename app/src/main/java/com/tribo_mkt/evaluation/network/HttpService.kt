package com.tribo_mkt.evaluation.network

import com.tribo_mkt.evaluation.model.UsuarioResposta
import retrofit2.http.GET

interface HttpService {

    @GET("users")
    suspend fun getUsuarios(): List<UsuarioResposta>
}