package com.tribo_mkt.evaluation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tribo_mkt.evaluation.model.AlbumResposta
import com.tribo_mkt.evaluation.repository.AlbunsRepository
import kotlinx.coroutines.launch

class AlbunsViewModel(private val albunsRepository: AlbunsRepository) : ViewModel() {

    val albunsData = MutableLiveData<List<AlbumResposta>>()
    val error = MutableLiveData<Boolean>()

    fun getAlbunsPerUser(userId : Int) {
        viewModelScope.launch {
            try {
                error.value = false
                albunsData.value = albunsRepository.getPerUser(userId)
            } catch (e: Exception) {
                error.value = true
            }
        }
    }
}