package com.example.eventhostmodule.ui.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.eventhostmodule.network.NetworkUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * SplashViewModel checks internet connectivity and exposes navigation events.
 * Add onboarding/guest logic here if needed in the future.
 */
class SplashViewModel(application: Application) : AndroidViewModel(application) {
    private val _isInternetAvailable = MutableLiveData<Boolean>()
    val isInternetAvailable: LiveData<Boolean> = _isInternetAvailable

    // Navigate to the next screen (onboarding or main). Renamed for clarity.
    private val _navigateToNext = MutableLiveData<Boolean>()
    val navigateToNext: LiveData<Boolean> = _navigateToNext

    fun checkInternetAndProceed() {
        viewModelScope.launch @androidx.annotation.RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE) {
            // Simulate splash delay
            delay(1000)
            val available = NetworkUtils.isInternetAvailable(getApplication())
            _isInternetAvailable.value = available
            if (available) {
                // Onboarding/guest logic would go here. Wait ~1s to show splash then navigate.
                // If onboarding flow is completed in the past, ViewModel could decide to go to MainActivity instead.
                _navigateToNext.value = true
            }
        }
    }
}
