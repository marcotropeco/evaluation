package com.tribo_mkt.evaluation.network

import com.tribo_mkt.evaluation.model.PostagemResposta
import retrofit2.http.Query

class ApiHelper(private val apiService: HttpService) {
    suspend fun getUsuarios() = apiService.getUsuarios()

    suspend fun getAlbunsUsuario(userId: Int) = apiService.getAlbunsUsuario(userId)

    suspend fun getComentariosUsuario(userId: Int) = apiService.getComentariosUsuario(userId)

    suspend fun getPostagensUsuario(userId: Int) = apiService.getPostagensUsuario(userId)
}