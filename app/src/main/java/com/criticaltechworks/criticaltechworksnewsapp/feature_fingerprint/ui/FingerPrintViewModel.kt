package com.criticaltechworks.criticaltechworksnewsapp.feature_fingerprint.ui

import android.content.Context
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.mutableStateOf
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.criticaltechworks.criticaltechworksnewsapp.feature_fingerprint.domain.BiometricAuthListener
import com.criticaltechworks.criticaltechworksnewsapp.feature_fingerprint.domain.usecase.CallFingerPrintAuthenticationUseCase
import com.criticaltechworks.criticaltechworksnewsapp.feature_fingerprint.domain.usecase.CanUseFingerPrintUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FingerPrintViewModel @Inject constructor(
    private val callFingerPrintAuthenticationUseCase: CallFingerPrintAuthenticationUseCase,
    private val canUseFingerPrintUseCase: CanUseFingerPrintUseCase
) : ViewModel(), BiometricAuthListener {

    private val _uiEvents: MutableSharedFlow<FingerPrintUIEvents> = MutableSharedFlow()
    val uiEvents: SharedFlow<FingerPrintUIEvents> = _uiEvents

    private val _canAuthenticate = mutableStateOf(true)
    val canAuthenticate =  _canAuthenticate

    fun canUseFingerPrint(context: Context): Boolean {
        return canUseFingerPrintUseCase(context)
    }

    fun authenticate(
        title: String = "Biometric Authentication",
        subtitle: String = "Enter biometric credentials to proceed.",
        description: String = "Use your Fingerprint",
        activity: FragmentActivity,
        listener: BiometricAuthListener = this,
        allowDeviceCredential: Boolean = false
    ) {
        _canAuthenticate.value = false
        callFingerPrintAuthenticationUseCase(
            title,
            subtitle,
            description,
            activity,
            listener,
            allowDeviceCredential
        )

    }

    private fun resetAuthentication(){
        _canAuthenticate.value = true
    }

    override fun onBiometricAuthenticationSuccess(result: BiometricPrompt.AuthenticationResult) {
        viewModelScope.launch {
            _uiEvents.emit(FingerPrintUIEvents.NavigateNewsList)
            resetAuthentication()
        }
    }

    override fun onBiometricAuthenticationFailed() {
        viewModelScope.launch {
            _uiEvents.emit(FingerPrintUIEvents.ShowSnackBar("This is a failed authentication"))
        }
    }

    override fun onBiometricAuthenticationError(errorCode: Int, errorMessage: String) {
        viewModelScope.launch {
            _uiEvents.emit(FingerPrintUIEvents.ShowSnackBar("Error Authentication, $errorCode with message $errorMessage"))
            resetAuthentication()
        }
    }

}