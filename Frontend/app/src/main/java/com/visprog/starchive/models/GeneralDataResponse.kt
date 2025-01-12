package com.visprog.starchive.models

import com.google.gson.annotations.SerializedName

data class GeneralDataResponse<T>(
    @SerializedName("data") val data: T
)
/*use this so if your backend decides to be funny and send a JSON inside a JSON as a response you can read it*/