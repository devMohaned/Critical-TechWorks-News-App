package com.criticaltechworks.criticaltechworksnewsapp.feature_fingerprint.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.criticaltechworks.criticaltechworksnewsapp.core.ui.theme.NormalButton
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.models.Article
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FingerPrintScreen(
    modifier: Modifier = Modifier,
    windowSize: WindowWidthSizeClass,
    onNewsListNavigation: () -> Unit
) {
    val viewModel: FingerPrintViewModel = hiltViewModel()
    val scaffoldState = rememberScaffoldState()
    val activity = LocalContext.current as FragmentActivity

    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.collectLatest {
            when (it) {
                is FingerPrintUIEvents.PopBack -> {}
                is FingerPrintUIEvents.NavigateNewsList -> onNewsListNavigation()
                is FingerPrintUIEvents.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        it.message
                    )
                }
            }
        }
    }

    Scaffold(scaffoldState = scaffoldState) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = "Authenticate using Finger Print to continue",
                textAlign = TextAlign.Center
            )

            NormalButton(enabled = viewModel.canAuthenticate.value, onClick = {
                viewModel.authenticate(
                    activity = activity,
                    allowDeviceCredential = false
                )
            }) {
                Text(text = "Authenticate")
            }
        }
    }


}