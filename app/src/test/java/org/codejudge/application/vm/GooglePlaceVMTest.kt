package org.codejudge.application.vm

import android.util.Log
import com.google.common.truth.Truth.assertThat
import nishan.softient.domain.entity.request.GooglePlaceRequest
import nishan.softient.domain.entity.wrapped.Resource
import org.codejudge.application.BaseTest
import org.codejudge.application.getOrAwaitTestValue
import org.codejudge.application.usecase.GooglePlaceFakeUseCase
import org.codejudge.application.usecase.UiState
import org.junit.Test
import timber.log.Timber


class GooglePlaceVMTest : BaseTest() {

    private lateinit var googlePlaceVM: GooglePlaceVM

    @Test
    fun placeApi_returnSuccess() {
        initVM(UiState.SUCCESS)
        googlePlaceVM.getPlace(GooglePlaceRequest())
        val result = googlePlaceVM.googlePlaceLiveData.getOrAwaitTestValue().peekContent() as Resource.Success
        println("placeApi_returnSuccess = $result")
        val value = result.data.size
        assertThat(value).isEqualTo(0)

    }

    @Test
    fun placeApi_returnError() {
        initVM(UiState.ERROR)
        googlePlaceVM.getPlace(GooglePlaceRequest())
        val result = googlePlaceVM.googlePlaceLiveData.getOrAwaitTestValue().peekContent() as Resource.Error
        println("placeApi_returnError = ${result.error}")
        assertThat(result).isInstanceOf(Resource.Error::class.java)
        assertThat(result.error.message).isEqualTo("Something went wrong")
    }

    private fun initVM(state: UiState) {
        googlePlaceVM = GooglePlaceVM(GooglePlaceFakeUseCase(state))
    }
}