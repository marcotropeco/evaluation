package com.tribo_mkt.evaluation.data

import com.tribo_mkt.evaluation.network.RetrofitBuilder
import com.tribo_mkt.evaluation.repository.fotos.FotosRepositoryImpl
import com.tribo_mkt.evaluation.testUtil.TestDataAlbuns.ALBUM
import com.tribo_mkt.evaluation.testUtil.TestDataPhotos.PHOTO_LIST
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert
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
class FotosRepositoryTest {

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
    fun `test success - getFotosAlbuns(FotosRepositoryImpl)`() = runBlockingTest {
        coEvery { apiHttpService.apiService.getFotosAlbuns(ALBUM.id.toInt()) } returns PHOTO_LIST
        val list = FotosRepositoryImpl(apiHttpService).getPerAlbum(ALBUM.id.toInt())
        assertTrue(list.size == 1)
        assertEquals(list[0].url, PHOTO_LIST[0].url)
        assertEquals(list[0].thumbnailUrl, PHOTO_LIST[0].thumbnailUrl)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test failed - getFotosAlbuns(FotosRepositoryImpl)`() = runBlockingTest {
        coEvery { apiHttpService.apiService.getFotosAlbuns(ALBUM.id.toInt()) } returns emptyList()
        val list = FotosRepositoryImpl(apiHttpService).getPerAlbum(ALBUM.id.toInt())
        Assert.assertFalse(list.size > 0)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `test failed - getFotosAlbuns(FotosRepositoryImpl) exception`() = runBlockingTest {
        coEvery { apiHttpService.apiService.getFotosAlbuns(ALBUM.id.toInt()) } coAnswers {
            throw Exception(
                "No network"
            )
        }
        var message: String? = null
        try {
            FotosRepositoryImpl(apiHttpService).getPerAlbum(ALBUM.id.toInt())
        } catch (e: Exception) {
            message = e.message
        }
        assertEquals(message, "No network")
    }
}