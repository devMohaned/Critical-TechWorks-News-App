package com.criticaltechworks.criticaltechworksnewsapp.feature_fingerprint.ui

sealed class FingerPrintUIEvents {
    object NavigateNewsList : FingerPrintUIEvents()
    object PopBack : FingerPrintUIEvents()
    class ShowSnackBar(val message: String) : FingerPrintUIEvents()

}