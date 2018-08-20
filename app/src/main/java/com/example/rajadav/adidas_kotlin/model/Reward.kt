package com.example.rajadav.adidas_kotlin.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Reward(@SerializedName("trophy") @Expose var trophy: String, @SerializedName("points") @Expose var points: Int)