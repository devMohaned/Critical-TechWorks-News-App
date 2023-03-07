package com.criticaltechworks.criticaltechworksnewsapp.feature_fingerprint.data

import android.content.Context
import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import com.criticaltechworks.criticaltechworksnewsapp.feature_fingerprint.domain.AuthenticatorManager
import com.criticaltechworks.criticaltechworksnewsapp.feature_fingerprint.domain.BiometricAuthListener
import java.util.concurrent.Executor


class FingerprintManagerImpl(private val mainExecutor: Executor) : AuthenticatorManager{


    override fun hasFingerprintCapability(context: Context): Int {
        val biometricManager = BiometricManager.from(context)
        return biometricManager.canAuthenticate(BIOMETRIC_STRONG)
    }

    override fun isBiometricReady(context: Context) =
        hasFingerprintCapability(context) == BiometricManager.BIOMETRIC_SUCCESS


    override fun setBiometricPromptInfo(
        title: String,
        subtitle: String,
        description: String,
        allowDeviceCredential: Boolean
    ): BiometricPrompt.PromptInfo {
        val builder = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(subtitle)
            .setDescription(description)

        builder.apply {
            if (allowDeviceCredential) setAllowedAuthenticators(BIOMETRIC_STRONG) // BIOMETRIC_STRONG to only use finger print
            setNegativeButtonText("Cancel")
        }

        return builder.build()
    }

    override fun initBiometricPrompt(
        activity: FragmentActivity,
        listener: BiometricAuthListener
    ): BiometricPrompt {

        // TODO: Remove Logs
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                listener.onBiometricAuthenticationError(errorCode, errString.toString())
                Log.w(this.javaClass.simpleName, "Authentication error code: $errorCode & msg: ${errString.toString()}")
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                listener.onBiometricAuthenticationFailed()
                Log.w(this.javaClass.simpleName, "Authentication failed for an unknown reason")
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                listener.onBiometricAuthenticationSuccess(result)
                Log.w(this.javaClass.simpleName, "Authentication success ${result.toString()})}")
            }
        }

        return BiometricPrompt(activity, mainExecutor, callback)
    }

}