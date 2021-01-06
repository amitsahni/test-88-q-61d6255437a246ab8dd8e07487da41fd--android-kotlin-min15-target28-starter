/**
 * Created by Amit Singh on 06/01/21.
 * Tila
 * asingh@tila.com
 */

package org.codejudge.application.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import nishan.softient.domain.entity.wrapped.EventResult

typealias MutableEventResultLiveData<R> = MutableLiveData<EventResult<R>>
typealias EventResultLiveData<R> = LiveData<EventResult<R>>