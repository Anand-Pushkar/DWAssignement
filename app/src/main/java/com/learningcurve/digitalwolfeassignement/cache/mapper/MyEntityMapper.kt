package com.learningcurve.digitalwolfeassignement.cache.mapper

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.learningcurve.digitalwolfeassignement.cache.entities.MyEntity
import com.learningcurve.digitalwolfeassignement.domain.model.MyModel
import com.learningcurve.digitalwolfeassignement.domain.util.DomainMapper
import com.learningcurve.digitalwolfeassignement.util.TAG
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class MyEntityMapper : DomainMapper<MyEntity, MyModel> {

    override fun mapToDomainModel(model: MyEntity): MyModel {
        Log.d(TAG, "mapToDomainModel: title = ${model.title}")
        Log.d(TAG, "mapToDomainModel: title = ${model.value}")
        return MyModel(
            title = model.title,
            type = model.type,
            value = model.value,
            startDateTime = model.startDateTime,
            endDateTime = model.endDateTime,
            unlimitedTime = model.unlimitedTime
        )
    }

    override fun mapFromDomainModel(domainModel: MyModel): MyEntity {
        Log.d(TAG, "mapFromDomainModel: title = ${domainModel.title}")
        Log.d(TAG, "mapFromDomainModel: value = ${domainModel.value}")
        return MyEntity(
            title = domainModel.title,
            type = domainModel.type,
            value = domainModel.value,
            startDateTime = domainModel.startDateTime,
            endDateTime = domainModel.endDateTime,
            unlimitedTime = domainModel.unlimitedTime
        )
    }

    fun toDomainList(initial: List<MyEntity>): List<MyModel>{
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<MyModel>): List<MyEntity>{
        return initial.map { mapFromDomainModel(it) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun dateTimeToString(dateTime: LocalDateTime): String {
        return dateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun dateTimeFromString(stringDateTime: String): LocalDateTime {
        return LocalDateTime.parse(stringDateTime, DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT))
    }
}