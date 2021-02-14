package com.tribo_mkt.evaluation.network

class ApiHelper(private val apiService: HttpService) {
    suspend fun getUsuarios() = apiService.getUsuarios()

    suspend fun getAlbunsUsuario(userId : Int) = apiService.getAlbunsUsuario(userId)
}