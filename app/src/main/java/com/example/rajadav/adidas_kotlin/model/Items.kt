package com.example.rajadav.adidas_kotlin.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//data class Items(var goals :List<Goal>? = null, var nextPageToken: String? ? =null)

//data class Items(@SerializedName("items")@Expose var _goals :List<Goal>? = null,@SerializedName("nextPageToken") @Expose var _description: String )


class Items{
    @SerializedName("items")
    @Expose
    lateinit var goals :List<Goal>

    @SerializedName("nextPageToken")
    @Expose
    var nextPageToken: String? ? =null

}
