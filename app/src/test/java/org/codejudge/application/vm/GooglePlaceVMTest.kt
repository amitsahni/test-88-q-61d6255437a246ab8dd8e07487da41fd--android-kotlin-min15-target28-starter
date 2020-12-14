package org.codejudge.application.vm

import androidx.lifecycle.asLiveData
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.GlobalScope
import nishan.softient.domain.entity.request.GooglePlaceRequest
import nishan.softient.domain.entity.wrapped.ResultError
import nishan.softient.domain.entity.wrapped.ResultSuccess
import org.codejudge.application.BaseTest
import org.codejudge.application.getOrAwaitTestValue
import org.codejudge.application.usecase.GooglePlaceFakeUseCase
import org.codejudge.application.usecase.UiState
import org.junit.Before
import org.junit.Test


class GooglePlaceVMTest : BaseTest() {

    private lateinit var placeFakeUseCase: GooglePlaceFakeUseCase
    private lateinit var googlePlaceVM: GooglePlaceVM

    @Before
    fun setUp() {
        placeFakeUseCase = GooglePlaceFakeUseCase(UiState.SUCCESS)
    }

    @Test
    fun placeApi_returnSuccess() {
        placeFakeUseCase.uiState = UiState.SUCCESS
        initVM()
        googlePlaceVM.post(PlaceSearchEvent.Place(GooglePlaceRequest()))
        when(val result = googlePlaceVM.googlePlaceLiveData.getOrAwaitTestValue().peekContent()){
            is ResultSuccess -> {
                assertThat(result.data.results.size).isEqualTo(0)
            }
        }

    }

    @Test
    fun placeApi_returnError() {
        placeFakeUseCase.uiState = UiState.ERROR
        initVM()
        googlePlaceVM.post(PlaceSearchEvent.Place(GooglePlaceRequest()))
        when (val result = googlePlaceVM.googlePlaceLiveData.getOrAwaitTestValue().peekContent()) {
            is ResultError -> {
                assertThat(result).isInstanceOf(ResultError::class.java)
                assertThat(result.error.message).isEqualTo("Something went wrong")
            }
        }
    }

    private fun initVM(){
        googlePlaceVM = GooglePlaceVM(placeFakeUseCase)
    }
}