package com.thanht.foodyentrytask.home.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thanht.domain.base.TaskSchedulers
import com.thanht.domain.city.CityListUseCase
import com.thanht.domain.city.SaveCityListUseCase
import com.thanht.domain.city.SearchCityListUseCase
import com.thanht.domain.model.CityModel
import com.thanht.foodyentrytask.util.toCityInfoList
import io.reactivex.observers.DisposableObserver
import kotlinx.coroutines.*
import javax.inject.Inject

private const val DEBOUNCE_TIME = 500L

class CityListViewModel @Inject constructor(
    private val cityListUseCase: CityListUseCase,
    private val saveCityListUseCase: SaveCityListUseCase,
    private val searchCityUseCase: SearchCityListUseCase,
    private val taskSchedulers: TaskSchedulers,
    private val coroutineScope: CoroutineScope
) : ViewModel() {

    private val _cityListResult = MutableLiveData<CityListResult>()
    val cityListResult: LiveData<CityListResult> = _cityListResult

    private var searchJob: Job? = null

    override fun onCleared() {
        cityListUseCase.unsubscribe()
        saveCityListUseCase.unsubscribe()
        searchJob?.cancel()
        super.onCleared()
    }

    fun getListCity() {
        cityListUseCase.apply {
            unsubscribe()
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
        searchJob = coroutineScope.launch {
            delay(DEBOUNCE_TIME)
            val cityList = searchCityUseCase.searchCity(keyword)
            if (cityList.isNotEmpty()) {
                withContext(Dispatchers.Main) {
                    _cityListResult.value = CityListResult(success = cityList.toCityInfoList())
                }
            } else {
                withContext(Dispatchers.Main) {
                    _cityListResult.value = CityListResult(error = "Can not get City List")
                }
            }
        }
    }

    private fun saveCityListToDB(result: List<CityModel>) {
        saveCityListUseCase.apply {
            unsubscribe()
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