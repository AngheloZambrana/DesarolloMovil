package com.example.loginconnav_saveargs

import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {
    fun logout() {
        SessionManager.clearSession()
    }
}