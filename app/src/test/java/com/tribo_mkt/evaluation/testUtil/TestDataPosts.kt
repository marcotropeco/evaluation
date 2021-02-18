package com.tribo_mkt.evaluation.testUtil

import com.tribo_mkt.evaluation.model.PostagemResposta

object TestDataPosts {

    val POSTS = PostagemResposta(
        id = "1",
        usuarioId = "1",
        titulo = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
        conteudo = "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
    )
    val POSTS_LIST = listOf(
        POSTS,
    )
}