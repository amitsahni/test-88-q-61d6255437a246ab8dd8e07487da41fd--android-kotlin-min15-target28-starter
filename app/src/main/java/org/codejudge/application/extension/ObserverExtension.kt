package org.codejudge.application.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import nishan.softient.domain.entity.response.base.Failure
import nishan.softient.domain.entity.wrapped.*
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
                is Resource.Loading -> {
                    if (loader)
                        activity?.showLoader()
                }

                is Resource.Success -> {
                    if (loader)
                        activity?.hideLoader()
                    success(value.data)
                }

                is Resource.Error -> {
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
                is Resource.Loading -> {
                    loading()
                }

                is Resource.Success -> {
                    success(value.data)
                }

                is Resource.Error -> {
                    error(value.error)
                }
            }
        }
    }
}

