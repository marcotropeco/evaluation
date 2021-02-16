package com.tribo_mkt.evaluation.testUtil

import com.tribo_mkt.evaluation.model.UsuarioResposta

object TestDataUsers {

    const val EMPTY_TEXT = ""
    const val BLANK_TEXT = "  "
    const val CONTENT_TEXT = "Testandoo..."
    const val VALID_EMAIL = "teste@teste.com"
    const val INVALID_EMAIL = "teste@teste"
    const val IS_LOADING = true
    const val NOT_LOADING = false
    const val HAS_SUCCESS = true
    const val HAS_ERROR = false

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