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

package com.example.android.marsrealestate.network


import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

enum class MarsApiFilter(val value: String) { SHOW_RENT("rent"), SHOW_BUY("buy"), SHOW_ALL("all") }

private const val BASE_URL = "https://mars.udacity.com/"

//In MarsApiService.kt, use the Moshi Builder to create a Moshi object with the KotlinJsonAdapterFactory
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

//Use Retrofit.Builder to create the Retrofit object.
//Add an instance of ScalarsConverterFactory and the BASE_URL we provided, then call build() to create the Retrofit object.
private val retrofit = Retrofit.Builder()
        //In the retrofit object, change the ConverterFactory to a MoshiConverterFactory with our moshi Object
        //.addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        //retrofit 2 Coroutine CallAdapter has become deprecated. It is recommended to migrate to Retrofit 2.6.0 or newer and use its built-in suspend support.
        //to use coroutines other than callbacks (they are both deprecated)
        //.addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()




//Create a MarsApiService interface, and define a getProperties() method to request the JSON response string.
//Annotate the method with @GET, specifying the endpoint for the JSON real estate response, and create the Retrofit Call object that will start the HTTP request.
interface MarsApiService {
    //filter added version
    //uses built-in coroutines
    @GET("realestate")
    suspend fun getProperties(@Query("filter") type: String): List<MarsProperty>



    //uses built-in coroutines
    @GET("realestate")
    suspend fun getProperties(): List<MarsProperty>

    //Removed the callbacks to use coroutines which is part of retrofit library.
    //Change getProperties() Call<List<MarsProperty>> to a list of MarsProperty
    //Deferred deprecated and coroutine call adapter deprecated
    /*
    @GET("realestate")
    suspend fun getProperties():
            Deferred<List<MarsProperty>>
    */

    //This solution uses callbacks.
    //Update MarsApiService getProperties() method to return a List of MarsProperty objects instead of String
    //This is for moshi
    /*@GET("realestate")
    fun getProperties():
            Call<List<MarsProperty>>
    */

   /*
   This is for scaler converter
   @GET("realestate")
    fun getProperties():
            Call<String>*/
}

//Passing in the service API you just defined, create a public object called MarsApi to expose the Retrofit service to the rest of the app:
object MarsApi {
    val retrofitService : MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}