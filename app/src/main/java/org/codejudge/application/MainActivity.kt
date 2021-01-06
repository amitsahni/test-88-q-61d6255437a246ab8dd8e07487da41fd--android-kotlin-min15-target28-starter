package org.codejudge.application

import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import nishan.softient.domain.entity.request.GooglePlaceRequest
import nishan.softient.domain.extension.debounceLaunch
import org.codejudge.application.databinding.ActivityMainBinding
import org.codejudge.application.extension.EventObserver
import org.codejudge.application.extension.viewBinding
import org.codejudge.application.ui.base.BaseAppCompatActivity
import org.codejudge.application.vm.GooglePlaceVM
import org.codejudge.application.vm.PlaceSearchEvent
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseAppCompatActivity() {

    private val googlePlaceVM by viewModel<GooglePlaceVM>()
    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val adapter by lazy {
        GooglePlaceAdapter()
    }

    override fun layout() = binding.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.rv.adapter = adapter
        googlePlaceVM.getPlace(GooglePlaceRequest())
        binding.editSearch.addTextChangedListener {
            it?.let {
                debounceLaunch {
                    googlePlaceVM.getPlace(GooglePlaceRequest(it.toString()))
                }
            }
        }
    }

    override fun observeLiveData() {
        googlePlaceVM.googlePlaceLiveData.observe(this, EventObserver(this) {
            adapter.submitList(it.results)
        })
    }
}
