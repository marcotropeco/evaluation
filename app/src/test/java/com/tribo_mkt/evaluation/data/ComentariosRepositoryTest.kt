package com.tribo_mkt.evaluation.data

import com.tribo_mkt.evaluation.network.RetrofitBuilder
import com.tribo_mkt.evaluation.repository.comentarios.ComentariosRepositoryImpl
import com.tribo_mkt.evaluation.testUtil.TestDataComments.COMMENT_LIST
import com.tribo_mkt.evaluation.testUtil.TestDataPosts.POSTS
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
class ComentariosRepositoryTest {

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
    fun `test success - getComentariosPostagens(AlbunsRepositoryImpl)`() = runBlockingTest {
        coEvery { apiHttpService.apiService.getComentariosPostagens(POSTS.id.toInt()) } returns COMMENT_LIST
        val list = ComentariosRepositoryImpl(apiHttpService).getPerPost(POSTS.id.toInt())
        assertTrue(list.size == 2)
        assertEquals(list[1].nome, COMMENT_LIST[1].nome)
        assertEquals(list[1].postagemId, COMMENT_LIST[1].postagemId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test failed - getComentariosPostagens(AlbunsRepositoryImpl)`() = runBlockingTest {
        coEvery { apiHttpService.apiService.getComentariosPostagens(POSTS.id.toInt()) } returns emptyList()
        val list = ComentariosRepositoryImpl(apiHttpService).getPerPost(POSTS.id.toInt())
        assertFalse(list.size > 0)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test failed - getComentariosPostagens(AlbunsRepositoryImpl) exception`() = runBlockingTest {
        coEvery { apiHttpService.apiService.getComentariosPostagens(POSTS.id.toInt()) } coAnswers {
            throw Exception(
                "No network"
            )
        }
        var message: String? = null
        try {
            ComentariosRepositoryImpl(apiHttpService).getPerPost(POSTS.id.toInt())
        } catch (e: Exception) {
            message = e.message
        }
        assertEquals(message, "No network")
    }
}