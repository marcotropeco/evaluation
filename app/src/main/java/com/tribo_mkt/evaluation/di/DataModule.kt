package com.tribo_mkt.evaluation.di

import com.tribo_mkt.evaluation.network.RetrofitBuilder
import com.tribo_mkt.evaluation.repository.UsuariosRepository
import com.tribo_mkt.evaluation.repository.UsuariosRepositoryImpl
import org.koin.dsl.module

val dataModule = module {
    factory { RetrofitBuilder() }
    factory<UsuariosRepository> { UsuariosRepositoryImpl(get()) }
}