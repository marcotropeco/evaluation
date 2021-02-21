package com.tribo_mkt.evaluation.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.tribo_mkt.evaluation.model.FotoResposta
import com.tribo_mkt.evaluation.repository.fotos.FotosRepository
import com.tribo_mkt.evaluation.testUtil.TestDataAlbuns.ALBUM
import com.tribo_mkt.evaluation.testUtil.TestDataPhotos.PHOTO_LIST
import com.tribo_mkt.evaluation.viewmodel.FotosViewModel
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FotosViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val repository = mockk<FotosRepository>()

    private val fotosLiveDataObserver = mockk<Observer<List<FotoResposta>>>(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when get fotos live data`() {
        val viewModel = instantiateViewModel()
        coEvery { repository.getPerAlbum(ALBUM.id.toInt()) } returns PHOTO_LIST

        viewModel.getPhotosPerAlbum(ALBUM.id.toInt())

        coVerifyOrder {
            repository.getPerAlbum(ALBUM.id.toInt())
            fotosLiveDataObserver.onChanged(PHOTO_LIST)
        }

        confirmVerified(repository)
        confirmVerified(fotosLiveDataObserver)
    }

    @Test
    fun `when get album live data with error`() {
        val viewModel = instantiateViewModel()
        coEvery { repository.getPerAlbum(ALBUM.id.toInt()) } throws Exception()

        viewModel.getPhotosPerAlbum(ALBUM.id.toInt())

        coVerifyOrder {
            repository.getPerAlbum(ALBUM.id.toInt())
        }
        confirmVerified(repository)
    }

    private fun instantiateViewModel(): FotosViewModel {
        val viewModel = FotosViewModel(repository)
        viewModel.fotosData.observeForever(fotosLiveDataObserver)
        return viewModel
    }
}