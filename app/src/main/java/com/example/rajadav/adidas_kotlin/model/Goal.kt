package com.example.rajadav.adidas_kotlin.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Goal(@SerializedName("title") @Expose var title: String, @SerializedName("description") @Expose val description: String ){

}