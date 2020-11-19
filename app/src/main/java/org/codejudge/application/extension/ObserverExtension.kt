package org.codejudge.application.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import nishan.softient.domain.entity.response.base.Failure
import nishan.softient.domain.entity.wrapped.EventResult
import nishan.softient.domain.entity.wrapped.ResultError
import nishan.softient.domain.entity.wrapped.ResultLoading
import nishan.softient.domain.entity.wrapped.ResultSuccess
import org.codejudge.application.ui.base.hideLoader
import org.codejudge.application.ui.base.showLoader
import org.codejudge.application.ui.base.snackBar

class EventObserver<T>(
    private val activity: AppCompatActivity? = null,
    private val loader: Boolean = true,
    private val notify: Boolean = true,
    private val success: (T) -> Unit
) : Observer<EventResult<T>> {
    override fun onChanged(event: EventResult<T>?) {
        event?.getContentIfNotHandled()?.let { value ->
            when (value) {
                is ResultLoading -> {
                    if (loader)
                        activity?.showLoader()
                }

                is ResultSuccess -> {
                    if (loader)
                        activity?.hideLoader()
                    success(value.data)
                }

                is ResultError -> {
                    if (loader)
                        activity?.hideLoader()
                    if (notify)
                        activity?.snackBar(value.error.message.toString())
                }
            }
        }
    }
}

class EventResultObserver<T>(
    private val loading: () -> Unit,
    private val success: (T) -> Unit,
    private val error: (Failure) -> Unit
) : Observer<EventResult<T>> {
    override fun onChanged(event: EventResult<T>?) {
        event?.getContentIfNotHandled()?.let { value ->
            when (value) {
                is ResultLoading -> {
                    loading()
                }

                is ResultSuccess -> {
                    success(value.data)
                }

                is ResultError -> {
                    error(value.error)
                }
            }
        }
    }
}

