package com.tribo_mkt.evaluation.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.tribo_mkt.evaluation.model.ComentarioResposta
import com.tribo_mkt.evaluation.repository.comentarios.ComentariosRepository
import com.tribo_mkt.evaluation.testUtil.TestDataComments.COMMENT
import com.tribo_mkt.evaluation.testUtil.TestDataComments.COMMENT_LIST
import com.tribo_mkt.evaluation.testUtil.TestDataComments.COMMENT_POST
import com.tribo_mkt.evaluation.viewmodel.ComentariosViewModel
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
class ComentariosViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val repository = mockk<ComentariosRepository>()

    private val comentarioLiveDataObserver =
        mockk<Observer<List<ComentarioResposta>>>(relaxed = true)

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
    fun `when get comments per posts live data`() {
        val viewModel = instantiateViewModel()
        coEvery { repository.getPerPost(COMMENT_POST.postagemId.toInt()) } returns COMMENT_LIST

        viewModel.getCommentsPerPost(COMMENT_POST.postagemId.toInt())

        coVerifyOrder {
            repository.getPerPost(COMMENT_POST.postagemId.toInt())
            comentarioLiveDataObserver.onChanged(COMMENT_LIST)
        }

        confirmVerified(repository)
        confirmVerified(comentarioLiveDataObserver)
    }

    @Test
    fun `when get comments live data with error`() {
        val viewModel = instantiateViewModel()
        coEvery { repository.getPerPost(COMMENT.postagemId.toInt()) } throws Exception()

        viewModel.getCommentsPerPost(COMMENT.postagemId.toInt())

        coVerifyOrder {
            repository.getPerPost(COMMENT.postagemId.toInt())
        }
        confirmVerified(repository)
    }

    private fun instantiateViewModel(): ComentariosViewModel {
        val viewModel = ComentariosViewModel(repository)
        viewModel.comentariosPostData.observeForever(comentarioLiveDataObserver)
        return viewModel
    }
}