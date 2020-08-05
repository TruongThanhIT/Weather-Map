package com.thanht.foodyentrytask.home.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thanht.foodyentrytask.R
import com.thanht.foodyentrytask.util.ListDiffAdapter

class CityListAdapter : ListDiffAdapter<CityInfo, CityListHolder>() {

    fun setData(data: List<CityInfo>) = submitList(data, 0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityListHolder {
        return CityListHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: CityListHolder, position: Int) {
        holder.bindData(data[position])
    }

    override fun getDiffUtilCallback(
        oldList: List<CityInfo>,
        newList: List<CityInfo>
    ): DiffUtil.Callback = CityDiffCallback(oldList, newList)

}