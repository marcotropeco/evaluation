package com.tribo_mkt.evaluation.data

import com.tribo_mkt.evaluation.network.RetrofitBuilder
import com.tribo_mkt.evaluation.repository.comentarios.ComentariosRepositoryImpl
import com.tribo_mkt.evaluation.repository.postagens.PostagensRepositoryImpl
import com.tribo_mkt.evaluation.testUtil.TestDataComments.COMMENT_LIST
import com.tribo_mkt.evaluation.testUtil.TestDataPosts.POSTS
import com.tribo_mkt.evaluation.testUtil.TestDataPosts.POSTS_LIST
import com.tribo_mkt.evaluation.testUtil.TestDataUsers.USER
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PostsRepositoryTest {

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()
    private val apiHttpService: RetrofitBuilder = mockk()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @ExperimentalCoroutinesApi
    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test success - getPostagensUsuario(PostagensRepositoryImpl)`() = runBlockingTest {
        coEvery { apiHttpService.apiService.getPostagensUsuario(USER.id.toInt()) } returns POSTS_LIST
        val list = PostagensRepositoryImpl(apiHttpService).getPerUser(USER.id.toInt())
        assertTrue(list.size == 1)
        assertEquals(list[0].titulo, POSTS_LIST[0].titulo)
        assertEquals(list[0].conteudo, POSTS_LIST[0].conteudo)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test failed - getPostagensUsuario(PostagensRepositoryImpl)`() = runBlockingTest {
        coEvery { apiHttpService.apiService.getPostagensUsuario(USER.id.toInt()) } returns emptyList()
        val list = PostagensRepositoryImpl(apiHttpService).getPerUser(USER.id.toInt())
        assertFalse(list.size > 0)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test failed - getPostagensUsuario(PostagensRepositoryImpl) exception`() = runBlockingTest {
        coEvery { apiHttpService.apiService.getPostagensUsuario(USER.id.toInt()) } coAnswers {
            throw Exception(
                "No network"
            )
        }
        var message: String? = null
        try {
            PostagensRepositoryImpl(apiHttpService).getPerUser(USER.id.toInt())
        } catch (e: Exception) {
            message = e.message
        }
        assertEquals(message, "No network")
    }
}