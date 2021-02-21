package com.tribo_mkt.evaluation.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.tribo_mkt.evaluation.model.PostagemResposta
import com.tribo_mkt.evaluation.repository.postagens.PostagensRepository
import com.tribo_mkt.evaluation.testUtil.TestDataPosts.POSTS
import com.tribo_mkt.evaluation.testUtil.TestDataPosts.POSTS_LIST
import com.tribo_mkt.evaluation.viewmodel.PostagensViewModel
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
class PostagensViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val repository = mockk<PostagensRepository>()

    private val postagemLiveDataObserver = mockk<Observer<List<PostagemResposta>>>(relaxed = true)

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
    fun `when get posts live data`() {
        val viewModel = instantiateViewModel()
        coEvery { repository.getPerUser(POSTS.usuarioId.toInt()) } returns POSTS_LIST

        viewModel.getPostsPerUser(POSTS.usuarioId.toInt())

        coVerifyOrder {
            repository.getPerUser(POSTS.usuarioId.toInt())
            postagemLiveDataObserver.onChanged(POSTS_LIST)
        }

        confirmVerified(repository)
        confirmVerified(postagemLiveDataObserver)
    }

    @Test
    fun `when get posts live data with error`() {
        val viewModel = instantiateViewModel()
        coEvery { repository.getPerUser(POSTS.usuarioId.toInt()) } throws Exception()

        viewModel.getPostsPerUser(POSTS.usuarioId.toInt())

        coVerifyOrder {
            repository.getPerUser(POSTS.usuarioId.toInt())
        }
        confirmVerified(repository)
    }

    private fun instantiateViewModel(): PostagensViewModel {
        val viewModel = PostagensViewModel(repository)
        viewModel.postagemData.observeForever(postagemLiveDataObserver)
        return viewModel
    }
}