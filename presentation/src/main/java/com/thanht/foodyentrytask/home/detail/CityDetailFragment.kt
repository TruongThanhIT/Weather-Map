package com.thanht.foodyentrytask.home.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.thanht.foodyentrytask.R
import kotlinx.android.synthetic.main.fragment_city_detail.*

private const val CITY_ID = "city_id"

class CityDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_city_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_label.text = "Hello id ${args.cityId}"
    }

    private val args: CityDetailFragmentArgs by navArgs()
    companion object {
        fun newInstance(cityId: Long) =
            CityDetailFragment().apply {
                arguments = Bundle().apply {
                    putLong(CITY_ID, cityId)
                }
            }
    }
}