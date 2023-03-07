package com.criticaltechworks.criticaltechworksnewsapp.feature_fingerprint.di

import android.content.Context
import androidx.core.content.ContextCompat
import com.criticaltechworks.criticaltechworksnewsapp.feature_fingerprint.data.FingerprintManagerImpl
import com.criticaltechworks.criticaltechworksnewsapp.feature_fingerprint.domain.AuthenticatorManager
import com.criticaltechworks.criticaltechworksnewsapp.feature_fingerprint.domain.usecase.CallFingerPrintAuthenticationUseCase
import com.criticaltechworks.criticaltechworksnewsapp.feature_fingerprint.domain.usecase.CanUseFingerPrintUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object FingerPrintModule {


    @Provides
    fun provideFingerPrintManager(@ApplicationContext context: Context): AuthenticatorManager {
        val executor = ContextCompat.getMainExecutor(context)
        return FingerprintManagerImpl(executor)
    }

    @Provides
    fun callFingerPrintAuthenticationUseCase(fingerprintManager: AuthenticatorManager): CallFingerPrintAuthenticationUseCase{
        return CallFingerPrintAuthenticationUseCase(fingerprintManager)
    }

    @Provides
    fun canUseFingerPrintUseCase(fingerprintManager: AuthenticatorManager): CanUseFingerPrintUseCase{
        return CanUseFingerPrintUseCase(fingerprintManager)
    }

}