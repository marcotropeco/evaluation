package com.tribo_mkt.evaluation.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.tribo_mkt.evaluation.model.UsuarioResposta
import com.tribo_mkt.evaluation.repository.usuarios.UsuariosRepository
import com.tribo_mkt.evaluation.testUtil.TestDataUsers.USERS_LIST
import com.tribo_mkt.evaluation.viewmodel.UsuariosViewModel
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
class UsuariosViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val repository = mockk<UsuariosRepository>()

    private val usersLiveDataObserver = mockk<Observer<List<UsuarioResposta>>>(relaxed = true)

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
    fun `when get users live data`() {
        val viewModel = instantiateViewModel()
        coEvery { repository.get() } returns USERS_LIST

        viewModel.getUsers()

        coVerifyOrder {
            repository.get()
            usersLiveDataObserver.onChanged(USERS_LIST)
        }

        confirmVerified(repository)
        confirmVerified(usersLiveDataObserver)
    }

    private fun instantiateViewModel(): UsuariosViewModel {
        val viewModel = UsuariosViewModel(repository)
        viewModel.usersData.observeForever(usersLiveDataObserver)
        return viewModel
    }

}