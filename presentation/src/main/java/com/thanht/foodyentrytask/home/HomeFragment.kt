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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.thanht.foodyentrytask.EventObserver
import com.thanht.foodyentrytask.databinding.FragmentHomeBinding
import com.thanht.foodyentrytask.home.list.CityListAdapter
import com.thanht.foodyentrytask.home.list.CityListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject
    lateinit var cityListViewModel: CityListViewModel

    private lateinit var adapter: CityListAdapter

    private lateinit var textWatcher: TextWatcher

    private lateinit var binding: FragmentHomeBinding

    private lateinit var recyclerview: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = cityListViewModel
        }
        recyclerview = binding.rvCity
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initEvents()
        observeViewModels()
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

    private fun observeViewModels() {
        cityListViewModel.cityListResult.observe(viewLifecycleOwner, Observer {
            val cityListResult = it ?: return@Observer
            cityListResult.error?.let { errorMsg ->
                showError(errorMsg)
            }
            cityListResult.success?.let { data ->
                adapter.submitList(data)
            }
        })

        cityListViewModel.navigateToCityDetail.observe(viewLifecycleOwner, EventObserver { navDirections ->
            findNavController().navigate(navDirections)
        })
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
        adapter = CityListAdapter(cityListViewModel, viewLifecycleOwner)
        recyclerview.apply {
            adapter = this@HomeFragment.adapter
            layoutManager = LinearLayoutManager(context)
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
    }
}