package com.tribo_mkt.evaluation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tribo_mkt.evaluation.model.ComentarioResposta
import com.tribo_mkt.evaluation.repository.comentarios.ComentariosRepository
import kotlinx.coroutines.launch

class ComentariosViewModel(private val comentariosRepository: ComentariosRepository) : ViewModel() {

    val comentariosData = MutableLiveData<List<ComentarioResposta>>()
    val comentariosPostData = MutableLiveData<List<ComentarioResposta>>()
    val error = MutableLiveData<Boolean>()

    fun getCommentsPerUser(userId : Int) {
        viewModelScope.launch {
            try {
                error.value = false
                comentariosData.value = comentariosRepository.getPerUser(userId)
            } catch (e: Exception) {
                error.value = true
            }
        }
    }

    fun getCommentsPerPost(postId : Int) {
        viewModelScope.launch {
            try {
                error.value = false
                comentariosPostData.value = comentariosRepository.getPerPost(postId)
            } catch (e: Exception) {
                error.value = true
            }
        }
    }
}