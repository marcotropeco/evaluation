package com.tribo_mkt.evaluation.repository

import com.tribo_mkt.evaluation.model.UsuarioResposta

interface UsuariosRepository {
    suspend fun get(): List<UsuarioResposta>
}