package org.codejudge.application

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.api.load
import nishan.softient.domain.entity.response.Result
import org.codejudge.application.databinding.ItemPlaceBinding
import org.codejudge.application.ui.base.BaseListAdapter
import org.codejudge.application.ui.base.DifCallback
import org.codejudge.application.ui.base.Holder

class GooglePlaceAdapter : BaseListAdapter<Holder, Result>(DifCallback()) {
    override fun onCreateItemViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        return Holder(
            ItemPlaceBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            ).root
        )
    }

    override fun onBindItemViewHolder(holder: Holder, position: Int) {
        val binding = ItemPlaceBinding.bind(holder.itemView)
        val item = getItem(position)
        binding.tvName.text = item.name
        binding.tvRating.text = item.rating
        binding.ivPlace.load(item.icon)
    }

}