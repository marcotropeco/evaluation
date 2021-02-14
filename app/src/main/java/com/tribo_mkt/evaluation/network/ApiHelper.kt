package com.tribo_mkt.evaluation.network

class ApiHelper(private val apiService: HttpService) {
    suspend fun getUsuarios() = apiService.getUsuarios()
}