package com.learningcurve.digitalwolfeassignement.cache.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "myTable")
data class MyEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "type")
    val type: Boolean,

    @ColumnInfo(name = "value")
    val value: String,

    @ColumnInfo(name = "startDateTime")
    val startDateTime: String,

    @ColumnInfo(name = "endDateTime")
    val endDateTime: String,

    @ColumnInfo(name = "unlimitedTime")
    val unlimitedTime: Boolean,

)