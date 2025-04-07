package com.avikus.assignment.android.boatstatus

import kotlinx.coroutines.flow.Flow

interface BoatStatusRepository {
    val boatStatus: Flow<BoatStatus>
}