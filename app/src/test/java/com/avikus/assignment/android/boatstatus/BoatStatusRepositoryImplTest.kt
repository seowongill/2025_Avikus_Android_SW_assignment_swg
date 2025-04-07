package com.avikus.assignment.android.boatstatus

import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class BoatStatusRepositoryImplTest {
    private lateinit var repository: BoatStatusRepositoryImpl
    private lateinit var defaultStatus: BoatStatus

    @Before
    fun setUp() {
        repository = BoatStatusRepositoryImpl()
        defaultStatus = BoatStatus(
            location = Coordinate(latitude = 0.0, longitude = 0.0),
            speed = 0f,
            heading = 0f
        )
    }

    @Test
    fun `generateNextStatus returns NaN values when toInvalidValue is true`() {
        val nextStatus = repository.generateNextStatus(defaultStatus, true)

        assertTrue(nextStatus.location.latitude.isNaN())
        assertTrue(nextStatus.location.longitude.isNaN())
        assertTrue(nextStatus.speed.isNaN())
        assertTrue(nextStatus.heading.isNaN())
    }
}