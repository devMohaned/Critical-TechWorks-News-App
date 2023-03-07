package com.criticaltechworks.criticaltechworksnewsapp.feature_fingerprint.domain

import android.content.Context
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity

interface AuthenticatorManager {
    fun hasFingerprintCapability(context: Context): Int
    fun isBiometricReady(context: Context): Boolean
    fun setBiometricPromptInfo(
        title: String,
        subtitle: String,
        description: String,
        allowDeviceCredential: Boolean
    ): BiometricPrompt.PromptInfo

    fun initBiometricPrompt(
        activity: FragmentActivity,
        listener: BiometricAuthListener
    ): BiometricPrompt
}