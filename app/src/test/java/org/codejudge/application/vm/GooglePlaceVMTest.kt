package org.codejudge.application.vm

import androidx.lifecycle.asFlow
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import nishan.softient.domain.entity.request.GooglePlaceRequest
import nishan.softient.domain.entity.wrapped.Resource
import org.codejudge.application.BaseTest
import org.codejudge.application.usecase.GooglePlaceFakeUseCase
import org.codejudge.application.usecase.UiState
import org.junit.Test


class GooglePlaceVMTest : BaseTest() {

    private lateinit var googlePlaceVM: GooglePlaceVM

    private val coroutineDispatcher = TestCoroutineDispatcher()

    @Test
    fun placeApi_returnSuccess() = runBlocking {
        initVM(UiState.SUCCESS)
        googlePlaceVM.getPlace(GooglePlaceRequest())
        delay(10L)
        val result =
            googlePlaceVM.googlePlaceLiveData.asFlow().first().peekContent() as Resource.Success
        println("runBlocking = $result")
        val value = result.data.size
        assertThat(value).isEqualTo(0)
    }

    @Test
    fun placeApi_returnError() = runBlocking {
        initVM(UiState.ERROR)
        googlePlaceVM.getPlace(GooglePlaceRequest())
        delay(10L)
        val result =
            googlePlaceVM.googlePlaceLiveData.asFlow().first().peekContent() as Resource.Error
        println("placeApi_returnError = $result")
        assertThat(result.error.message).isEqualTo("Something went wrong")
    }

    private fun initVM(state: UiState) {
        googlePlaceVM = GooglePlaceVM(GooglePlaceFakeUseCase(state))
    }
}