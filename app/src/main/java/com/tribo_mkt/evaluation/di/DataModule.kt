package com.tribo_mkt.evaluation.di

import com.tribo_mkt.evaluation.network.RetrofitBuilder
import com.tribo_mkt.evaluation.repository.*
import org.koin.dsl.module

val dataModule = module {
    factory { RetrofitBuilder() }
    factory<UsuariosRepository> { UsuariosRepositoryImpl(get()) }
    factory<AlbunsRepository> { AlbunsRepositoryImpl(get()) }
    factory<ComentariosRepository> { ComentariosRepositoryImpl(get()) }
    factory<PostagensRepository> { PostagensRepositoryImpl(get()) }
    factory<FotosRepository> { FotosRepositoryImpl(get()) }
}