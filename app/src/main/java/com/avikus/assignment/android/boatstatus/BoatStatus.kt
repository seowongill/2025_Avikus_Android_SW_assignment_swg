package com.avikus.assignment.android.boatstatus

data class BoatStatus(
    val location: Coordinate,
    val speed: Float,
    val heading: Float,
)

data class Coordinate(
    val latitude: Double,
    val longitude: Double,
)