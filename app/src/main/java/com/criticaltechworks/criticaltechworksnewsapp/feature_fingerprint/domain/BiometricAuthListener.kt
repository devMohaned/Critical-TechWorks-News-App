package com.criticaltechworks.criticaltechworksnewsapp.feature_fingerprint.domain

import androidx.biometric.BiometricPrompt


interface BiometricAuthListener {
    fun onBiometricAuthenticationSuccess(result: BiometricPrompt.AuthenticationResult)
    fun onBiometricAuthenticationFailed()
    fun onBiometricAuthenticationError(errorCode: Int, errorMessage: String)
}