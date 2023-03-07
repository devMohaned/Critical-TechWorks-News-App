package com.criticaltechworks.criticaltechworksnewsapp.feature_fingerprint.domain.usecase

import androidx.fragment.app.FragmentActivity
import com.criticaltechworks.criticaltechworksnewsapp.feature_fingerprint.data.FingerprintManagerImpl
import com.criticaltechworks.criticaltechworksnewsapp.feature_fingerprint.domain.AuthenticatorManager
import com.criticaltechworks.criticaltechworksnewsapp.feature_fingerprint.domain.BiometricAuthListener

class CallFingerPrintAuthenticationUseCase(private val fingerprintManager: AuthenticatorManager) {

    operator fun invoke(
        title: String,
        subtitle: String,
        description: String,
        activity: FragmentActivity,
        listener: BiometricAuthListener,
        allowDeviceCredential: Boolean
    ){
        val promptInfo = fingerprintManager.setBiometricPromptInfo(
            title,
            subtitle,
            description,
            allowDeviceCredential
        )

        val biometricPrompt = fingerprintManager.initBiometricPrompt(activity, listener)
        biometricPrompt.authenticate(promptInfo)

    }
}