package com.tribo_mkt.evaluation.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.tribo_mkt.evaluation.model.AlbumResposta
import com.tribo_mkt.evaluation.repository.albuns.AlbunsRepository
import com.tribo_mkt.evaluation.testUtil.TestDataAlbuns.ALBUM
import com.tribo_mkt.evaluation.testUtil.TestDataAlbuns.ALBUM_LIST
import com.tribo_mkt.evaluation.viewmodel.AlbunsViewModel
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
class AlbunsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val repository = mockk<AlbunsRepository>()

    private val albumLiveDataObserver = mockk<Observer<List<AlbumResposta>>>(relaxed = true)

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
    fun `when get album live data`() {
        val viewModel = instantiateViewModel()
        coEvery { repository.getPerUser(ALBUM.usuarioId.toInt()) } returns ALBUM_LIST

        viewModel.getAlbunsPerUser(ALBUM.usuarioId.toInt())

        coVerifyOrder {
            repository.getPerUser(ALBUM.usuarioId.toInt())
            albumLiveDataObserver.onChanged(ALBUM_LIST)
        }

        confirmVerified(repository)
        confirmVerified(albumLiveDataObserver)
    }

    @Test
    fun `when get album live data with error`() {
        val viewModel = instantiateViewModel()
        coEvery { repository.getPerUser(ALBUM.usuarioId.toInt()) } throws Exception()

        viewModel.getAlbunsPerUser(ALBUM.usuarioId.toInt())

        coVerifyOrder {
            repository.getPerUser(ALBUM.usuarioId.toInt())
        }
        confirmVerified(repository)
    }

    private fun instantiateViewModel(): AlbunsViewModel {
        val viewModel = AlbunsViewModel(repository)
        viewModel.albunsData.observeForever(albumLiveDataObserver)
        return viewModel
    }

}