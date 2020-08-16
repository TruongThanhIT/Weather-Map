package com.thanht.foodyentrytask.home.list

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.thanht.foodyentrytask.databinding.ItemCityBinding

class CityListHolder(
    private val binding: ItemCityBinding,
    private val cityListViewModel: CityListViewModel,
    private val viewLifecycleOwner: LifecycleOwner
) : RecyclerView.ViewHolder(binding.root) {

    fun bindData(data: CityInfo) {
        binding.apply {
            cityInfo = data
            viewmodel = cityListViewModel
            lifecycleOwner = viewLifecycleOwner
            executePendingBindings()
        }
    }
}