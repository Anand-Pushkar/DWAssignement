package com.learningcurve.digitalwolfeassignement.presentataion.ui.edit

import com.learningcurve.digitalwolfeassignement.domain.model.MyModel

sealed class EditScreenEvent {

    data class GetDataByTitleEvent(
        val title: String
    ): EditScreenEvent()

    object InsertEntryIntoDatabaseEvent: EditScreenEvent()

    object UpdateEntryInDatabaseEvent: EditScreenEvent()
}
