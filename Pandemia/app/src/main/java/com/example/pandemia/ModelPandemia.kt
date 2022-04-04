package com.example.pandemia

import com.google.gson.annotations.SerializedName

data class ModelPandemia (
    @SerializedName("country") var country:String?,
    @SerializedName("cases") var cases:String?,
    @SerializedName("recovered") var recovered:String?,
    @SerializedName("population") var population:String?
        )