package com.avikus.assignment.android.boatstatus

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class BoatStatusRepositoryImpl : BoatStatusRepository {
    private var currentStatus = BoatStatus(
        location = Coordinate(latitude = 0.0, longitude = 0.0),
        heading = 0.0f,
        speed = 0.0f
    )

    override val boatStatus: Flow<BoatStatus> = flow {
        while (true) {
            val next = generateNextStatus(currentStatus, Random.nextInt(100) < 20)
            currentStatus = next
            emit(next)
            delay(200L)
        }
    }

    internal fun generateNextStatus(previous: BoatStatus, toInvalidValue: Boolean): BoatStatus {
        return if (toInvalidValue) {
            BoatStatus(
                location = Coordinate(Double.NaN, Double.NaN),
                speed = Float.NaN,
                heading = Float.NaN
            )
        } else {
            BoatStatus(
                location = Coordinate(
                    latitude = if (previous.location.latitude.isNaN()) 0.0
                    else previous.location.latitude + Random.nextDouble(-0.01, 0.01),
                    longitude = if (previous.location.longitude.isNaN()) 0.0
                    else previous.location.longitude + Random.nextDouble(-0.01, 0.01)
                ),
                speed = if (previous.speed.isNaN()) 0.0f else previous.speed + Random.nextFloat(),
                heading = (if (previous.heading.isNaN()) 0.0f else previous.heading + Random.nextFloat()) % 360f
            )
        }
    }
}
