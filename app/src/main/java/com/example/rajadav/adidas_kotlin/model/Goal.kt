package com.example.rajadav.adidas_kotlin.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "goal")
class Goal(@PrimaryKey() @ColumnInfo(name = "title") @SerializedName("title") @Expose var title: String, @ColumnInfo(name = "description") @SerializedName("description") @Expose val description: String ){

}