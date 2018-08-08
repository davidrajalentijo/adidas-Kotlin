package com.example.rajadav.adidas_kotlin.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Items(@SerializedName("items")@Expose var goals :List<Goal>, @SerializedName("nextPageToken") @Expose var nextPageToken: String){}
