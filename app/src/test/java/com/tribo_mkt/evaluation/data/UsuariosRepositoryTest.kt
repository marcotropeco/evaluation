package com.tribo_mkt.evaluation.data

import com.tribo_mkt.evaluation.network.RetrofitBuilder
import com.tribo_mkt.evaluation.repository.usuarios.UsuariosRepositoryImpl
import com.tribo_mkt.evaluation.testUtil.TestDataUsers.USERS_LIST
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
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
class UsuariosRepositoryTest {

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
    fun `test success - getUsuarios(UsuariosRepositoryImpl)`() = runBlockingTest {
        coEvery { apiHttpService.apiService.getUsuarios() } returns USERS_LIST
        val list = UsuariosRepositoryImpl(apiHttpService).get()
        assertTrue(list.size == 1)
        assertEquals(list[0].nome, USERS_LIST[0].nome)
        assertEquals(list[0].telefone, USERS_LIST[0].telefone)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test failed - getUsuarios(UsuariosRepositoryImpl) exception`() = runBlockingTest {
        coEvery { apiHttpService.apiService.getUsuarios() } coAnswers { throw Exception("No network") }
        var message: String? = null
        try {
            UsuariosRepositoryImpl(apiHttpService).get()
        } catch (e: java.lang.Exception) {
            message = e.message
        }
        assertEquals(message, "No network")
    }
}