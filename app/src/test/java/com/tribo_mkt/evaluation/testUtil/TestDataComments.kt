package com.tribo_mkt.evaluation.testUtil

import com.tribo_mkt.evaluation.model.ComentarioResposta

object TestDataComments {

    val USER_ID = 1

    val COMMENT = ComentarioResposta(
        postagemId = "1",
        id = "1",
        email = "Eliseo@gardner.biz",
        nome = "id labore ex et quam laborum",
        conteudo = "laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium"
    )

    val COMMENT_POST = ComentarioResposta(
        postagemId = "1",
        id = "2",
        email = "Jayne_Kuhic@sydney.com",
        nome = "quo vero reiciendis velit similique earum",
        conteudo = "est natus enim nihil est dolore omnis voluptatem numquam\net omnis occaecati quod ullam at\nvoluptatem error expedita pariatur\nnihil sint nostrum voluptatem reiciendis et"
    )

    val COMMENT_LIST = listOf(
        COMMENT,
        COMMENT_POST,
    )
}