package com.tribo_mkt.evaluation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tribo_mkt.evaluation.model.FotoResposta
import com.tribo_mkt.evaluation.repository.FotosRepository
import kotlinx.coroutines.launch

class FotosViewModel(private val fotosRepository: FotosRepository) : ViewModel() {

    val fotosData = MutableLiveData<List<FotoResposta>>()
    val error = MutableLiveData<Boolean>()

    fun getPhotosPerAlbum(albumId : Int) {
        viewModelScope.launch {
            try {
                error.value = false
                fotosData.value = fotosRepository.getPerAlbum(albumId)
            } catch (e: Exception) {
                error.value = true
            }
        }
    }
}