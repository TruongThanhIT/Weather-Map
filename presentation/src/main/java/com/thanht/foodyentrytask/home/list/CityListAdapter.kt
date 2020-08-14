package com.thanht.foodyentrytask.home.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.thanht.foodyentrytask.databinding.ItemCityBinding

class CityListAdapter : ListAdapter<CityInfo, CityListHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityListHolder {
        val binding = ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityListHolder(binding)
    }

    override fun onBindViewHolder(holder: CityListHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CityInfo>() {
            override fun areItemsTheSame(oldItem: CityInfo, newItem: CityInfo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CityInfo, newItem: CityInfo): Boolean {
                return oldItem.equals(newItem)
            }
        }
    }
}
