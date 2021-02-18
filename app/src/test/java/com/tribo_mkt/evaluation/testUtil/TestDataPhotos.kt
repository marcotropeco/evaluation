package com.tribo_mkt.evaluation.testUtil

import com.tribo_mkt.evaluation.model.FotoResposta

object TestDataPhotos {

    val PHOTO = FotoResposta(
        albumId = "1",
        id = "1",
        titulo = "accusamus beatae ad facilis cum similique qui sunt",
        url = "https://via.placeholder.com/600/92c952",
        thumbnailUrl = "https://via.placeholder.com/150/92c952"
    )
    val PHOTO_LIST = listOf(
        PHOTO,
    )
}