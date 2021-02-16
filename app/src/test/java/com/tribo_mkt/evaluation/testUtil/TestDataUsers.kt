package com.tribo_mkt.evaluation.testUtil

import com.tribo_mkt.evaluation.model.UsuarioResposta

object TestDataUsers {

    val USER = UsuarioResposta(
        id = "1",
        nome = "Leanne Graham",
        usuarioNome = "Bret",
        email = "Sincere@april.biz",
        telefone = "1-770-736-8031 x56442",
    )
    val USERS_LIST = listOf(
        USER,
    )
}