package com.thanht.foodyentrytask.home.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.thanht.foodyentrytask.R

class CityListAdapter : ListAdapter<CityInfo, CityListHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityListHolder {
        return CityListHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        )
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
