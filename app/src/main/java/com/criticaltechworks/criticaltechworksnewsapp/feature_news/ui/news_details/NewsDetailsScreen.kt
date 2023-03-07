package com.criticaltechworks.criticaltechworksnewsapp.feature_news.ui.news_details

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.criticaltechworks.criticaltechworksnewsapp.core.ui.theme.NormalButton
import com.criticaltechworks.criticaltechworksnewsapp.core.ui.theme.SPACING_DOUBLE
import com.criticaltechworks.criticaltechworksnewsapp.core.ui.theme.SPACING_NORMAL
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.ui.news_list.*
import com.criticaltechworks.criticaltechworksnewsapp.R
import com.criticaltechworks.criticaltechworksnewsapp.core.ui.components.HandleAdaptiveLayout
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.models.Article

object NewsDetailsLinking {
    const val ARG_1_TITLE = "arg_title"
    const val ARG_2_PUBLISH_AT = "arg_publish_at"
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NewsDetailScreen(
    modifier: Modifier = Modifier,
    windowSize: WindowWidthSizeClass,
    title: String,
    publishedAt: String,
    onBackButtonPressed: () -> Unit
) {
    val viewModel: NewsDetailsViewModel = hiltViewModel<NewsDetailsViewModel>()
    val uiState = viewModel.uiState.value
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.fetchData(title, publishedAt)
    }

    Scaffold(scaffoldState = scaffoldState,
        topBar = {
            NewsDetailAppBar(onBackButtonPressed = onBackButtonPressed)
        }
    ) { _ ->
        if (uiState.isLoading) {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            HandleAdaptiveLayout(
                windowSize = windowSize,
                CompactLayout = { NewsDetailBody(article = uiState.article) },
                MediumLayout = { NewsDetailBody(article = uiState.article) },
                ExpandedLayout = { LargerNewsDetailBody(article = uiState.article) }
            ) {
                NewsDetailBody(article = uiState.article)
            }
        }
    }
}

@Composable
fun NewsDetailAppBar(
    modifier: Modifier = Modifier,
    onBackButtonPressed: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = { BackButtonIcon(onBackButtonPressed = onBackButtonPressed) },
        title = { NewsDetailsTitle() }
    )
}

@Composable
fun NewsDetailsTitle(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(id = R.string.headline_details),
        style = MaterialTheme.typography.h3,
        modifier = modifier
    )
}


@Composable
fun BackButtonIcon(modifier: Modifier = Modifier, onBackButtonPressed: () -> Unit) {
    IconButton(modifier = modifier, onClick = onBackButtonPressed) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = stringResource(
                id = R.string.back_button
            ),
        )
    }
}

@Composable
fun NewsDetailBody(modifier: Modifier = Modifier, article: Article) {
    LazyColumn {
        item() {
            if (!article.urlToImage.isNullOrBlank()) {
                NewsDetailImage(
                    modifier = modifier.padding(horizontal = SPACING_NORMAL),
                    urlToImage = article.urlToImage, content = article.content ?: ""
                )
            }
        }

        item {
            if (article.title.isNotBlank()) {
                Text(
                    modifier = modifier.padding(horizontal = SPACING_NORMAL),
                    text = article.title, style = MaterialTheme.typography.h3
                )
            }
        }

        item() {
            if (!article.description.isNullOrBlank()) {
                Text(
                    modifier = modifier.padding(horizontal = SPACING_NORMAL),
                    text = article.description, style = MaterialTheme.typography.body1
                )
            }
        }

        item() {
            Row(
                modifier = modifier.padding(horizontal = SPACING_NORMAL),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (!article.author.isNullOrBlank()) {
                    Text(
                        modifier = modifier
                            .padding(horizontal = SPACING_NORMAL)
                            .weight(1f),
                        text = article.author,
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center
                    )
                }
                if (article.publishedAt.isNotBlank()) {
                    Text(
                        modifier = modifier
                            .padding(horizontal = SPACING_NORMAL)
                            .weight(1f),
                        text = article.publishedAt,
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center
                    )
                }
            }

        }

        item {
            if (!article.url.isNullOrBlank()) {
                VisitWebsiteButton(url = article.url)
            }
        }

        item {
            if (!article.content.isNullOrBlank()) {
                Text(
                    modifier = modifier.padding(horizontal = SPACING_NORMAL),
                    text = article.content,
                    style = MaterialTheme.typography.subtitle1
                )
            }
        }


    }
}

@Composable
fun NewsDetailImage(modifier: Modifier = Modifier, urlToImage: String, content: String) {
    AsyncImage(
        modifier = modifier
            .fillMaxHeight(0.7f)
            .fillMaxWidth(),
        model = urlToImage,
        contentDescription = content
    )
}

@Composable
fun VisitWebsiteButton(modifier: Modifier = Modifier, url: String) {
    val uriHandler = LocalUriHandler.current
    NormalButton(
        modifier = modifier
            .padding(horizontal = SPACING_DOUBLE, vertical = SPACING_NORMAL)
            .fillMaxWidth(), onClick = { uriHandler.openUri(url) }
    ) {
        Text(text = stringResource(id = R.string.visit_website))
    }

}


@Composable
fun LargerNewsDetailBody(modifier: Modifier = Modifier, article: Article) {
    LazyColumn {
        item() {
            Row {
                Column(modifier.fillMaxWidth(0.4f)) {
                    if (!article.urlToImage.isNullOrBlank()) {
                        NewsDetailImage(
                            modifier = modifier.padding(horizontal = SPACING_NORMAL),
                            urlToImage = article.urlToImage, content = article.content ?: ""
                        )
                    }
                }
                Column(modifier = modifier.weight(1f)) {
                    if (article.title.isNotBlank()) {
                        Text(
                            modifier = modifier.padding(horizontal = SPACING_NORMAL),
                            text = article.title, style = MaterialTheme.typography.h3
                        )
                    }

                    Row(horizontalArrangement = Arrangement.SpaceAround) {
                        Column {
                            if (!article.author.isNullOrBlank()) {
                                Text(
                                    modifier = modifier
                                        .padding(horizontal = SPACING_NORMAL),
                                    text = article.author,
                                    style = MaterialTheme.typography.body1,
                                    textAlign = TextAlign.Center
                                )
                            }

                            if (article.publishedAt.isNotBlank()) {
                                Text(
                                    modifier = modifier
                                        .padding(horizontal = SPACING_NORMAL),
                                    text = article.publishedAt,
                                    style = MaterialTheme.typography.body1,
                                    textAlign = TextAlign.Center
                                )
                            }

                        }
                        Column {
                            if (!article.url.isNullOrBlank()) {
                                VisitWebsiteButton(modifier= modifier.fillMaxWidth(),url = article.url)
                            }
                        }
                    }
                }
            }

        }


        item() {
            if (!article.description.isNullOrBlank()) {
                Text(
                    modifier = modifier.padding(horizontal = SPACING_NORMAL),
                    text = article.description, style = MaterialTheme.typography.body1
                )
            }
        }



        item {
            Divider(modifier = modifier.padding(SPACING_NORMAL))
        }

        item {
            if (!article.content.isNullOrBlank()) {
                Text(
                    modifier = modifier.padding(horizontal = SPACING_NORMAL),
                    text = article.content,
                    style = MaterialTheme.typography.subtitle1
                )
            }
        }


    }
}