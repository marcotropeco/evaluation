package com.tribo_mkt.evaluation.di

import com.tribo_mkt.evaluation.viewmodel.AlbunsViewModel
import com.tribo_mkt.evaluation.viewmodel.ComentariosViewModel
import com.tribo_mkt.evaluation.viewmodel.PostagensViewModel
import com.tribo_mkt.evaluation.viewmodel.UsuariosViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { UsuariosViewModel(get()) }
    viewModel { AlbunsViewModel(get()) }
    viewModel { ComentariosViewModel(get()) }
    viewModel { PostagensViewModel(get()) }
}