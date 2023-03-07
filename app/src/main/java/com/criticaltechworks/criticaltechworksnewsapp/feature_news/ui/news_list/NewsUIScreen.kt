package com.criticaltechworks.criticaltechworksnewsapp.feature_news.ui.news_list

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.criticaltechworks.criticaltechworksnewsapp.core.ui.components.HandleAdaptiveLayout
import com.criticaltechworks.criticaltechworksnewsapp.core.ui.theme.SPACING_NORMAL
import com.criticaltechworks.criticaltechworksnewsapp.core.ui.theme.SPACING_SMALL
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.models.Article
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.models.Source
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.models.Sources
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.ui.dialog.Source
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.ui.dialog.NewsSourcesAlertDialog
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.ui.news_details.NewsDetailBody
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NewsScreen(
    modifier: Modifier = Modifier,
    windowSize: WindowWidthSizeClass,
    onNewsDetailNavigation: (article: Article) -> Unit
) {
    val viewModel: NewsViewModel = hiltViewModel<NewsViewModel>()
    val uiState = viewModel.uiState.value
    val isDialogVisible = viewModel.isDialogVisibleUIState
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        if (uiState.newsList.isEmpty())
            viewModel.onSearchSubmit() // Get the list only if it was empty/first launched
    }


    HandleUIEvents(
        isDialogVisible.value,
        viewModel.uiEvents,
        showDialog = { viewModel.updateDialogVisibility(true) },
        scaffoldState = scaffoldState
    )

    HandleAdaptiveLayout(
        windowSize = windowSize,
        CompactLayout = {
            CompactLayout(
                scaffoldState = scaffoldState,
                uiState = uiState,
                isDialogVisible = isDialogVisible.value,
                viewModel = viewModel,
                onNewsDetailNavigation = onNewsDetailNavigation
            )
        },
        MediumLayout = {
            MediumLayout(
                scaffoldState = scaffoldState,
                uiState = uiState,
                isDialogVisible = isDialogVisible.value,
                viewModel = viewModel,
                onNewsDetailNavigation = { article ->
                    viewModel.updateSelectedArticle(article = article)
                },
            )
        },
        ExpandedLayout = {
            ExpandedLayout(
                scaffoldState = scaffoldState,
                uiState = uiState,
                viewModel = viewModel,
                onNewsDetailNavigation = { article ->
                    viewModel.updateSelectedArticle(article = article)
                },
            )
        }
    ) {
        CompactLayout(
            scaffoldState = scaffoldState,
            uiState = uiState,
            isDialogVisible = isDialogVisible.value,
            viewModel = viewModel,
            onNewsDetailNavigation = onNewsDetailNavigation
        )
    }


}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CompactLayout(
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState,
    uiState: NewsUIState,
    isDialogVisible: Boolean,
    viewModel: NewsViewModel,
    onNewsDetailNavigation: (article: Article) -> Unit
) {

    HandleDialog(isDialogVisible,
        onNewsSourceClicked = {
            viewModel.updateNewsSource(it)
            viewModel.onSearchSubmit()
        },
        onDismissClicked = { viewModel.updateDialogVisibility(false) })

    Scaffold(scaffoldState = scaffoldState,
        topBar = {
            NewsAppBar(title = uiState.source.name, onNewsSourceIconClicked = {
                viewModel.showDialog()
            })
        }
    ) { _ ->

        if (uiState.isLoading)
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

        if (uiState.newsList.isEmpty()) Text(
            modifier = modifier.fillMaxWidth(),
            text = "No Content Available",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h3
        )
        else NewsBody(newsList = uiState.newsList, onNewsDetailNavigation = onNewsDetailNavigation)
    }
}



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MediumLayout(
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState,
    uiState: NewsUIState,
    isDialogVisible: Boolean,
    viewModel: NewsViewModel,
    onNewsDetailNavigation: (article: Article) -> Unit,
) {

    HandleDialog(isDialogVisible,
        onNewsSourceClicked = {
            viewModel.updateNewsSource(it)
            viewModel.onSearchSubmit()
        },
        onDismissClicked = { viewModel.updateDialogVisibility(false) })

    Scaffold(scaffoldState = scaffoldState,
        topBar = {
            NewsAppBar(title = uiState.source.name, onNewsSourceIconClicked = {
                viewModel.showDialog()
            })
        }
    ) { _ ->

        if (uiState.isLoading)
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

        if (uiState.newsList.isEmpty()) Text(
            modifier = modifier.fillMaxWidth(),
            text = "No Content Available",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h3
        )
        else {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(
                    modifier = modifier
                        .padding(horizontal = SPACING_NORMAL)
                        .fillMaxWidth(0.4f),
                ) {
                    NewsBody(
                        newsList = uiState.newsList,
                        onNewsDetailNavigation = onNewsDetailNavigation
                    )
                }

                Column(
                    modifier = modifier
                        .padding(horizontal = SPACING_NORMAL)
                        .weight(1f),
                ) {
                    NewsDetailBody(article =viewModel.selectedArticle.value)
                }
            }

        }
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ExpandedLayout(
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState,
    uiState: NewsUIState,
    viewModel: NewsViewModel,
    onNewsDetailNavigation: (article: Article) -> Unit,
    sources: Array<Source> = Sources,

    ) {

    Scaffold(scaffoldState = scaffoldState,
        topBar = {
            NewsAppBar(title = uiState.source.name, isActionsEnabled = false, onNewsSourceIconClicked = {})
        }
    ) { _ ->

        if (uiState.isLoading)
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

        if (uiState.newsList.isEmpty()) Text(
            modifier = modifier.fillMaxWidth(),
            text = "No Content Available",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h3
        )
        else {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(
                    modifier = modifier
                        .padding(horizontal = SPACING_NORMAL)
                        .fillMaxWidth(0.20f),
                ) {
                    LazyColumn(modifier = modifier.fillMaxHeight()) {
                        items(sources.size)
                        {
                            Source(sourceName = sources[it].name, onSourecClicked = {
                                viewModel.updateNewsSource(sources[it])
                                viewModel.onSearchSubmit()
                            })
                        }
                    }
                }


                Column(
                    modifier = modifier
                        .padding(horizontal = SPACING_NORMAL)
                        .fillMaxWidth(0.35f),
                ) {
                    NewsBody(
                        newsList = uiState.newsList,
                        onNewsDetailNavigation = onNewsDetailNavigation
                    )
                }

                Column(
                    modifier = modifier
                        .padding(horizontal = SPACING_NORMAL)
                        .weight(1f),
                ) {
                    NewsDetailBody(article =viewModel.selectedArticle.value)
                }
            }

        }
    }
}


@Composable
fun NewsBody(
    modifier: Modifier = Modifier,
    newsList: List<Article>,
    onNewsDetailNavigation: (article: Article) -> Unit
) {

    LazyColumn(modifier = modifier)
    {
        items(newsList.size)
        { index ->
            val current = newsList[index]
            NewsItem(article = current, onNewsDetailNavigation = onNewsDetailNavigation)
        }
    }
}

@Composable
fun NewsItem(
    modifier: Modifier = Modifier,
    article: Article,
    onNewsDetailNavigation: (article: Article) -> Unit
) {
    Column(
        modifier
            .padding(SPACING_NORMAL)
            .fillMaxSize()
            .background(MaterialTheme.colors.secondaryVariant)
            .clickable {
                onNewsDetailNavigation(article)
            }
    ) {
        AsyncImage(
            modifier = modifier
                .fillMaxHeight(0.7f)
                .fillMaxWidth(),
            model = article.urlToImage,
            contentDescription = article.content
        )
        Text(
            modifier = modifier
                .padding(top = SPACING_SMALL), text = article.publishedAt,
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = modifier,
            text = article.title,
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center

        )

    }
}


@Composable
private fun HandleDialog(
    isDialogVisible: Boolean,
    sources: Array<Source> = Sources,
    onNewsSourceClicked: (source: Source) -> Unit,
    onDismissClicked: () -> Unit
) {
    if (isDialogVisible) {
        NewsSourcesAlertDialog(
            sources = sources,
            onNewsSourceSelected = onNewsSourceClicked,
            onDismissRequest = onDismissClicked
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HandleUIEvents(
    isDialogVisible: Boolean,
    uiEvent: SharedFlow<NewsUIEvent>,
    showDialog: () -> Unit,
    scaffoldState: ScaffoldState
) {


    LaunchedEffect(key1 = isDialogVisible)
    {
        uiEvent.collectLatest { event ->
            when (event) {
                is NewsUIEvent.ShowDialog -> showDialog()
                is NewsUIEvent.ShowSnackBar -> scaffoldState.snackbarHostState.showSnackbar(
                    event.message
                )
            }
        }
    }
}