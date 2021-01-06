package org.codejudge.application.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

typealias Collect<T> = (data: T) -> Unit
abstract class BaseVM : ViewModel() {
    val viewModelIOScope = viewModelScope.plus(Dispatchers.IO)

    fun <T> Flow<T>.toLiveData() = asLiveData(viewModelIOScope.coroutineContext)

    inline fun <T> Flow<T>.collectFlow(crossinline block: Collect<T>) = viewModelIOScope.launch {
        collect {
            block(it)
        }
    }

}