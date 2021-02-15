package com.tribo_mkt.evaluation.di

import com.tribo_mkt.evaluation.network.RetrofitBuilder
import com.tribo_mkt.evaluation.repository.albuns.AlbunsRepository
import com.tribo_mkt.evaluation.repository.albuns.AlbunsRepositoryImpl
import com.tribo_mkt.evaluation.repository.comentarios.ComentariosRepository
import com.tribo_mkt.evaluation.repository.comentarios.ComentariosRepositoryImpl
import com.tribo_mkt.evaluation.repository.fotos.FotosRepository
import com.tribo_mkt.evaluation.repository.fotos.FotosRepositoryImpl
import com.tribo_mkt.evaluation.repository.postagens.PostagensRepository
import com.tribo_mkt.evaluation.repository.postagens.PostagensRepositoryImpl
import com.tribo_mkt.evaluation.repository.usuarios.UsuariosRepository
import com.tribo_mkt.evaluation.repository.usuarios.UsuariosRepositoryImpl
import org.koin.dsl.module

val dataModule = module {
    factory { RetrofitBuilder() }
    factory<UsuariosRepository> { UsuariosRepositoryImpl(get()) }
    factory<AlbunsRepository> { AlbunsRepositoryImpl(get()) }
    factory<ComentariosRepository> { ComentariosRepositoryImpl(get()) }
    factory<PostagensRepository> { PostagensRepositoryImpl(get()) }
    factory<FotosRepository> { FotosRepositoryImpl(get()) }
}