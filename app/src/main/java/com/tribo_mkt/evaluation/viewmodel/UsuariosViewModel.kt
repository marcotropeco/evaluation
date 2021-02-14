package com.tribo_mkt.evaluation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tribo_mkt.evaluation.model.UsuarioResposta
import com.tribo_mkt.evaluation.repository.UsuariosRepository
import kotlinx.coroutines.launch

class UsuariosViewModel(private val usuariosRepository: UsuariosRepository) : ViewModel() {

    val usersData = MutableLiveData<List<UsuarioResposta>>()
    val error = MutableLiveData<Boolean>()

    fun getUsuarios() {
        viewModelScope.launch {
            try {
                error.value = false
                usersData.value = usuariosRepository.get()
            } catch (e: Exception) {
                error.value = true
            }
        }
    }
}