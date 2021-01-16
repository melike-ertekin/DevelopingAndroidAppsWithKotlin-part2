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

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

//In MarsProperty.kt, convert the class to a Kotlin data class with properties that match the JSON response fields.
//Rename the img_src class property to imgSrcUrl, and add a @Json annotation to remap the img_src JSON field to it:
@Parcelize
data class MarsProperty(
        val id: String?,
        @Json(name = "img_src")
        val imgSrcUrl: String?,
        val type: String?,
        val price: Double):Parcelable{
        val isRental
                get() = type == "rent"



}


//not needed anymore. @Parcelize is better to use
/*{
        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readDouble()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(id)
                parcel.writeString(imgSrcUrl)
                parcel.writeString(type)
                parcel.writeDouble(price)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<MarsProperty> {
                override fun createFromParcel(parcel: Parcel): MarsProperty {
                        return MarsProperty(parcel)
                }

                override fun newArray(size: Int): Array<MarsProperty?> {
                        return arrayOfNulls(size)
                }
        }
}
*/