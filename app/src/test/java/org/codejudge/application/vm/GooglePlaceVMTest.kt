package org.codejudge.application.vm

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.google.common.truth.Truth.assertThat
import nishan.softient.domain.entity.request.GooglePlaceRequest
import nishan.softient.domain.entity.wrapped.Resource
import nishan.softient.domain.entity.wrapped.error
import nishan.softient.domain.entity.wrapped.success
import org.codejudge.application.BaseTest
import org.codejudge.application.getOrAwaitTestValue
import org.codejudge.application.observeOnce
import org.codejudge.application.usecase.GooglePlaceFakeUseCase
import org.codejudge.application.usecase.UiState
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito


class GooglePlaceVMTest : BaseTest() {

    private lateinit var googlePlaceVM: GooglePlaceVM

    @Before
    fun setUp() {
    }

    @Test
    fun placeApi_returnSuccess() {
        initVM(UiState.SUCCESS)
        googlePlaceVM.getPlace(GooglePlaceRequest())
        val result = googlePlaceVM.googlePlaceLiveData.getOrAwaitTestValue().peekContent() as Resource.Success
        val value = result.data.results.size
        assertThat(value).isEqualTo(2)

    }

    @Test
    fun placeApi_returnError() {
        initVM(UiState.ERROR)
        googlePlaceVM.getPlace(GooglePlaceRequest())
        val result = googlePlaceVM.googlePlaceLiveData.getOrAwaitTestValue().peekContent() as Resource.Error
        assertThat(result).isInstanceOf(Resource.Error::class.java)
        assertThat(result.error.message).isEqualTo("Something went wrong!!")
    }

    private fun initVM(state: UiState) {
        googlePlaceVM = GooglePlaceVM(GooglePlaceFakeUseCase(state))
        googlePlaceVM.isTesting = true
    }
}