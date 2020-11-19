package org.codejudge.application.ui.base

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

typealias ItemClickListener<T> = (position: Int, data: T) -> Unit
typealias ItemLongClickListener<T> = (position: Int, data: T) -> Unit
typealias ItemViewClickListener<T> = (id: Int, position: Int, data: T) -> Unit
class Holder(view: View) : RecyclerView.ViewHolder(view)
abstract class BaseListAdapter<VH : RecyclerView.ViewHolder, T>(callback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, RecyclerView.ViewHolder>(callback) {
    internal var itemClickListener: ItemClickListener<T>? = null
    internal var itemLongClickListener: ItemLongClickListener<T>? = null
    internal var itemViewClickListener: ItemViewClickListener<T>? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return onCreateItemViewHolder(viewGroup, viewType)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindItemViewHolder(holder as VH, position)
    }

    fun click(f: ItemClickListener<T>) {
        itemClickListener = f
    }

    fun longClick(f: ItemLongClickListener<T>) {
        itemLongClickListener = f
    }

    fun iconClick(f: ItemViewClickListener<T>) {
        itemViewClickListener = f
    }

    abstract fun onCreateItemViewHolder(viewGroup: ViewGroup, viewType: Int): VH

    abstract fun onBindItemViewHolder(holder: VH, position: Int)
}

class DifCallback<T> :
    DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem === newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

}

