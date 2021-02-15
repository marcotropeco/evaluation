package com.tribo_mkt.evaluation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tribo_mkt.evaluation.model.PostagemResposta
import com.tribo_mkt.evaluation.repository.PostagensRepository
import kotlinx.coroutines.launch

class PostagensViewModel(private val postagensRepository: PostagensRepository) : ViewModel() {

    val postagemData = MutableLiveData<List<PostagemResposta>>()
    val error = MutableLiveData<Boolean>()

    fun getPostagensPerUser(userId : Int) {
        viewModelScope.launch {
            try {
                error.value = false
                postagemData.value = postagensRepository.getPerUser(userId)
            } catch (e: Exception) {
                error.value = true
            }
        }
    }
}