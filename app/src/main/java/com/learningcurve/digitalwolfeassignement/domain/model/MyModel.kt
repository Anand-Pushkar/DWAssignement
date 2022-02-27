package com.learningcurve.digitalwolfeassignement.domain.model

import java.time.LocalDateTime

// business model
class MyModel(
    val title: String,
    val type: Boolean,
    val value: String,
    val startDateTime: String,
    val endDateTime: String,
    val unlimitedTime: Boolean,
    val typeString: String = if(type) { "Percentage" } else { "Monetary" },
    val valueString: String = if(type) { "${value}%" } else { "£${value}" }
)