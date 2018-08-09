package com.example.rajadav.adidas_kotlin.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "goal")
class Goal(@PrimaryKey() @ColumnInfo(name = "id") val id: String, @ColumnInfo(name = "title") @SerializedName("title") @Expose var title: String,
           @ColumnInfo(name = "description") @SerializedName("description") @Expose var description: String,
           @ColumnInfo(name = "type") @SerializedName("type") @Expose var type: String,
           @ColumnInfo(name = "goal") @SerializedName("goal") @Expose var goal: Int,
           @ColumnInfo(name = "steps") @SerializedName("steps") @Expose var steps: Int,
           @ColumnInfo(name = "distance") @SerializedName("distance") @Expose var distance: Int,
           @Embedded var reward: Reward){

    companion object {
        const val BRONZE_REWARD:String = "bronze_medal"
        const val SILVER_REWARD:String = "silver_medal"
        const val GOLD_REWARD:String = "gold_medal"
        const val ZOMBIE_REWARD:String = "zombie_hand"
    }
}