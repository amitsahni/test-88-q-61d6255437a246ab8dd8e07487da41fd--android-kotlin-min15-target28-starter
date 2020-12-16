package org.codejudge.application.di

import coil.ImageLoaderBuilder
import coil.util.Logger
import nishan.softient.data.datasource.remote.GooglePlaceApi
import nishan.softient.data.datasource.remote.RetrofitManager
import nishan.softient.data.repository.GooglePlaceRepoImpl
import nishan.softient.domain.manager.GooglePlaceDataManager
import nishan.softient.domain.repository.GooglePlaceRepo
import nishan.softient.domain.usecase.GooglePlaceBaseUseCase
import nishan.softient.domain.usecase.GooglePlaceUseCase
import org.codejudge.application.BuildConfig
import org.codejudge.application.vm.GooglePlaceVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import timber.log.Timber

fun dependency() = listOf(vm, repository, manager, service, useCases, singleInstance)

val vm = module {
    viewModel { GooglePlaceVM(get()) }
}

val useCases = module {
    // PlaceDataManager
    factory { GooglePlaceUseCase(get()) as GooglePlaceBaseUseCase}
}

val manager = module {
    // GooglePlaceRepo
    single { GooglePlaceDataManager(get()) }
}

val repository = module {
    // GooglePlaceApi
    single { GooglePlaceRepoImpl(get()) as GooglePlaceRepo }
}

val service = module {
    single { GooglePlaceApi.create(get()) }
}

val singleInstance = module {
    single {
        ImageLoaderBuilder(get()).apply {
            availableMemoryPercentage(0.25)
            crossfade(true)
            logger(object : Logger {
                override var level: Int
                    get() = 1
                    set(value) {
                    }

                override fun log(
                    tag: String,
                    priority: Int,
                    message: String?,
                    throwable: Throwable?
                ) {
                    Timber.d(message)
                }

            })
        }.build()
    }
    single {
        val manager = RetrofitManager(get())
        manager.retrofit(BuildConfig.BASE_URL)
        manager
    }
    single { get<RetrofitManager>().retrofit(BuildConfig.BASE_URL) }
}