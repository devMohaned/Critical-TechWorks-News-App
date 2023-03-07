package com.criticaltechworks.criticaltechworksnewsapp.feature_fingerprint.domain.usecase

import android.content.Context
import com.criticaltechworks.criticaltechworksnewsapp.feature_fingerprint.data.FingerprintManagerImpl
import com.criticaltechworks.criticaltechworksnewsapp.feature_fingerprint.domain.AuthenticatorManager

class CanUseFingerPrintUseCase(
    private val fingerprintManager: AuthenticatorManager,
) {

    operator fun invoke(context: Context): Boolean {
        return fingerprintManager.isBiometricReady(context)
    }
}