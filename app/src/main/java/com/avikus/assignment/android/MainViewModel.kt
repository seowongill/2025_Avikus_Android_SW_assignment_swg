package com.avikus.assignment.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avikus.assignment.android.boatstatus.BoatStatus
import com.avikus.assignment.android.boatstatus.BoatStatusRepository
import com.avikus.assignment.android.boatstatus.BoatStatusRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repo: BoatStatusRepository = BoatStatusRepositoryImpl()

    private val _boatStatus = MutableStateFlow<BoatStatus?>(null)
    val boatStatus: StateFlow<BoatStatus?> = _boatStatus

    init {
        viewModelScope.launch {
            repo.boatStatus.collectLatest { status ->
                _boatStatus.value = status
            }
        }
    }
}
