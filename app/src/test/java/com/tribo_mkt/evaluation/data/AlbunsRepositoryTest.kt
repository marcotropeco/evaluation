package com.tribo_mkt.evaluation.data

import com.tribo_mkt.evaluation.network.RetrofitBuilder
import com.tribo_mkt.evaluation.repository.albuns.AlbunsRepositoryImpl
import com.tribo_mkt.evaluation.testUtil.TestDataAlbuns.ALBUM_LIST
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
class AlbunsRepositoryTest {

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
    fun `test success - getAlbunsUsuario(AlbunsRepositoryImpl)`() = runBlockingTest {
        coEvery { apiHttpService.apiService.getAlbunsUsuario(USER.id.toInt()) } returns ALBUM_LIST
        val list = AlbunsRepositoryImpl(apiHttpService).getPerUser(USER.id.toInt())
        assertTrue(list.size == 1)
        assertEquals(list[0].titulo, ALBUM_LIST[0].titulo)
        assertEquals(list[0].usuarioId, USER.id)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `test failed - getAlbunsUsuario(AlbunsRepositoryImpl)`() = runBlockingTest {
        coEvery { apiHttpService.apiService.getAlbunsUsuario(USER.id.toInt()) } returns emptyList()
        val list = AlbunsRepositoryImpl(apiHttpService).getPerUser(USER.id.toInt())
        assertFalse(list.size > 0)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test failed - getAlbunsUsuario(AlbunsRepositoryImpl) exception`() = runBlockingTest {
        coEvery { apiHttpService.apiService.getAlbunsUsuario(USER.id.toInt()) } coAnswers {
            throw Exception(
                "No network"
            )
        }
        var message: String? = null
        try {
            AlbunsRepositoryImpl(apiHttpService).getPerUser(USER.id.toInt())
        } catch (e: Exception) {
            message = e.message
        }
        assertEquals(message, "No network")
    }
}