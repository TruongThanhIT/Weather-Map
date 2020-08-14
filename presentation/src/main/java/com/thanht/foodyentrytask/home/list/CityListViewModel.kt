package com.thanht.foodyentrytask.home.list

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thanht.domain.base.TaskSchedulers
import com.thanht.domain.city.CityListUseCase
import com.thanht.domain.city.SaveCityListUseCase
import com.thanht.domain.city.SearchCityListUseCase
import com.thanht.domain.model.CityModel
import com.thanht.foodyentrytask.util.toCityInfoList
import io.reactivex.observers.DisposableObserver
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

private const val DEBOUNCE_TIME = 500L

class CityListViewModel @Inject constructor(
    private val cityListUseCase: CityListUseCase,
    private val saveCityListUseCase: SaveCityListUseCase,
    private val searchCityUseCase: SearchCityListUseCase,
    private val taskSchedulers: TaskSchedulers
) : ViewModel() {

    private val _cityListResult = MutableLiveData<CityListResult>()
    val cityListResult: LiveData<CityListResult> = _cityListResult

    private var searchJob: Job? = null

    override fun onCleared() {
        cityListUseCase.unsubscribe()
        saveCityListUseCase.unsubscribe()
        super.onCleared()
    }

    fun getListCity() {
        cityListUseCase.apply {
            execute(object : DisposableObserver<List<CityModel>>() {
                override fun onComplete() {
                }

                override fun onNext(result: List<CityModel>) {
                    _cityListResult.value = CityListResult(success = result.toCityInfoList())
                    saveCityListToDB(result)
                }

                override fun onError(e: Throwable) {
                    _cityListResult.value = CityListResult(error = e.message)
                }

            }, taskSchedulers)
        }
    }

    fun searchCity(keyword: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(DEBOUNCE_TIME)
            searchCityUseCase.searchCity(keyword).collect { cityList ->
                if (cityList.isNotEmpty()) {
                    _cityListResult.value = CityListResult(success = cityList.toCityInfoList())
                } else {
                    _cityListResult.value = CityListResult(error = "Empty City List")
                }
            }
        }
    }

    private fun saveCityListToDB(result: List<CityModel>) {
        saveCityListUseCase.apply {
            setParam(result)
            execute(object : DisposableObserver<Unit>() {
                override fun onComplete() {
                }

                override fun onNext(t: Unit) {
                }

                override fun onError(e: Throwable) {
                }

            }, taskSchedulers)
        }
    }
}