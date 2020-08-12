package com.thanht.foodyentrytask.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.thanht.foodyentrytask.R
import com.thanht.foodyentrytask.ext.userComponent
import com.thanht.foodyentrytask.home.list.CityInfo
import com.thanht.foodyentrytask.home.list.CityListAdapter
import com.thanht.foodyentrytask.home.list.CityListViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var cityListViewModel: CityListViewModel

    private val adapter = CityListAdapter()

    private lateinit var textWatcher: TextWatcher

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requireActivity().userComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initEvents()
        cityListViewModel.cityListResult.observe(requireActivity(), Observer {
            val cityListResult = it ?: return@Observer
            cityListResult.error?.let { errorMsg ->
                showError(errorMsg)
            }
            cityListResult.success?.let { data ->
                adapter.submitList(data)
            }
        })
        cityListViewModel.getListCity()
    }

    override fun onResume() {
        super.onResume()
        et_city.addTextChangedListener(textWatcher)
    }

    override fun onPause() {
        super.onPause()
        et_city.removeTextChangedListener(textWatcher)
    }

    private fun initEvents() {
        textWatcher = object : TextWatcher {
            private var searchFor = ""
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().trim()
                if (searchText != searchFor) {
                    searchFor = searchText
                    cityListViewModel.searchCity(searchFor)
                }
            }

        }
    }

    private fun showError(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    private fun initUI() {
        rv_city.apply {
            layoutManager = LinearLayoutManager(context)
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            adapter = this@HomeFragment.adapter
        }
    }
}