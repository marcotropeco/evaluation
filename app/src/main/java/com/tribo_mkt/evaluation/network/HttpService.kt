package com.tribo_mkt.evaluation.network

import com.tribo_mkt.evaluation.model.AlbumResposta
import com.tribo_mkt.evaluation.model.ComentarioResposta
import com.tribo_mkt.evaluation.model.PostagemResposta
import com.tribo_mkt.evaluation.model.UsuarioResposta
import retrofit2.http.GET
import retrofit2.http.Query

interface HttpService {

    @GET("users")
    suspend fun getUsuarios(): List<UsuarioResposta>

    @GET("albums")
    suspend fun getAlbunsUsuario(@Query("userId") userId: Int): List<AlbumResposta>

    @GET("comments")
    suspend fun getComentariosUsuario(@Query("userId") userId: Int): List<ComentarioResposta>

    @GET("posts")
    suspend fun getPostagensUsuario(@Query("userId") userId: Int): List<PostagemResposta>

}