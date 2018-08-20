package com.example.rajadav.adidas_kotlin.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "goalsdone")
class CompletedGoal(@SerializedName("goalid") @Expose var goalid: Int,
                    @SerializedName("title") @Expose var title: String,
                    @SerializedName("points") @Expose var points: Int,
                    @SerializedName("trophy") @Expose var thropy: String,
                    @SerializedName("day") @Expose var day: Int,
                    @SerializedName("month") @Expose var month: Int,
                    @SerializedName("year") @Expose var year: Int,
                    @SerializedName("hour") @Expose var hour: Int,
                    @SerializedName("minutes") @Expose var minutes: Int,
                    @SerializedName("seconds") @Expose var seconds: Int) {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    var id: Int = 0
}