/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.marsrealestate.network.MarsApi
import com.example.android.marsrealestate.network.MarsApiFilter
import com.example.android.marsrealestate.network.MarsProperty
import kotlinx.coroutines.launch

enum class MarsApiStatus { LOADING, ERROR, DONE }
/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {
    // not needed anymore
    // The internal MutableLiveData String that stores the status of the most recent request
    /*private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the request status String
    val status: LiveData<String>
        get() = _status
*/


  private val _status = MutableLiveData<MarsApiStatus>()
    val status: LiveData<MarsApiStatus>
        get() = _status


    private val _properties = MutableLiveData<List<MarsProperty>>()

    val properties: LiveData<List<MarsProperty>>
        get() = _properties

    private val _navigateToSelectedProperty = MutableLiveData<MarsProperty>()

    val navigateToSelectedProperty: LiveData<MarsProperty>
        get() = _navigateToSelectedProperty

    fun displayPropertyDetails(marsProperty: MarsProperty) {
        _navigateToSelectedProperty.value = marsProperty
    }
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

    //no more needed!
    /*private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _status
    */
    //no more needed!
    /*
    private val _property = MutableLiveData<MarsProperty>()
    val property: LiveData<MarsProperty>
        get() = _property
       */


    //Remember, creating own scope is no longer recommended by Google.
    //Therefore, it is not required to add variable for a coroutine Job and a CoroutineScope using the Main Dispatcher anymore.
    // Instead, you can use the default viewModelScope
     //private var viewModelJob = Job()
     //private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )



    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        //getMarsRealEstateProperties()
        getMarsRealEstateProperties(MarsApiFilter.SHOW_ALL)
    }

    private fun getMarsRealEstateProperties(filter: MarsApiFilter) {

        viewModelScope.launch {
            _status.value = MarsApiStatus.LOADING
            try {
                var listResult = MarsApi.retrofitService.getProperties(filter.value)
                //_status.value = "Success: ${listResult.size} Mars properties retrieved"
                _status.value = MarsApiStatus.DONE

                if (listResult.size > 0) {
                    _properties.value = listResult
                    // _property.value = listResult[0]
                }
            } catch (e: Exception) {
                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList()
                // _status.value = "Failure: ${e.message}"
            }


        }
    }

    fun updateFilter(filter: MarsApiFilter) {
        getMarsRealEstateProperties(filter)
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
    private fun getMarsRealEstateProperties() {


        viewModelScope.launch {
            _status.value = MarsApiStatus.LOADING
            try {
                var listResult = MarsApi.retrofitService.getProperties()
                //_status.value = "Success: ${listResult.size} Mars properties retrieved"
                _status.value = MarsApiStatus.DONE

                if (listResult.size > 0) {
                    _properties.value = listResult
                   // _property.value = listResult[0]
                }
            } catch (e: Exception) {
                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList()
               // _status.value = "Failure: ${e.message}"
            }


        }

        //Remember, creating own scope is no longer recommended by Google.
        //Therefore, it is not required to add variable for a coroutine Job and a CoroutineScope using the Main Dispatcher anymore.
        // Instead, you can use the default viewModelScope

        /*coroutineScope.launch{
            var getPropertiesRefered = MarsApi.retrofitService.getProperties()
            try {
                var listResult = getPropertiesRefered.await()
                _response.value = "Success: ${listResult.size} Mars properties retrieved"
            }catch (t:Throwable){
                _response.value = "Failure: " + t.message
            }

            }
        }*/



        //In OverviewModel.kt, update getMarsRealEstateProperties() to handle list of MarsProperty instead of String.
        //This uses callbacks which is deprecated.
        /*MarsApi.retrofitService.getProperties().enqueue( object: Callback<List<MarsProperty>> {
            override fun onFailure(call: Call<List<MarsProperty>>, t: Throwable) {
                _response.value = "Failure: " + t.message
            }

            override fun onResponse(call: Call<List<MarsProperty>>, response: Response<List<MarsProperty>>) {
                _response.value = "Success: ${response.body()?.size} Mars properties retrieved"
            }
        })*/


        /*
        This is for scaler converter
        MarsApi.retrofitService.getProperties().enqueue( object: Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                _response.value = "Failure: " + t.message
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                _response.value = response.body()
            }
        })*/
    }
}
