package com.criticaltechworks.criticaltechworksnewsapp.feature_news.ui.news_list
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.criticaltechworks.criticaltechworksnewsapp.core.ui.theme.SPACING_NORMAL
import com.criticaltechworks.criticaltechworksnewsapp.R
@Composable
fun NewsAppBar(
    modifier: Modifier = Modifier,
    title: String,
    isActionsEnabled: Boolean = true,
    onNewsSourceIconClicked: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            AppBarIcon()
        },
        title = {
            AppBarTitle(title = title)
        },
        actions = {
         if(isActionsEnabled)   AppMenuActions(onNewsSourceIconClicked = onNewsSourceIconClicked)
        }
    )
}

@Composable
fun AppBarIcon(modifier: Modifier = Modifier) {
    Icon(
        modifier = modifier.padding(SPACING_NORMAL),
        painter = painterResource(id = R.drawable.ic_launcher),
        contentDescription = stringResource(id = R.string.app_icon),
        tint = Color.Unspecified
    )
}

@Composable
fun AppBarTitle(modifier: Modifier = Modifier, title: String) {
    Text(modifier = modifier, text = title, style = MaterialTheme.typography.h3)
}

@Composable
fun AppMenuActions(
    modifier: Modifier = Modifier,
    onNewsSourceIconClicked: () -> Unit,
) {
    AppMenuItem(
        modifier = modifier,
        drawableResId = R.drawable.ic_source,
        stringResId = R.string.available_sources,
        onClick = onNewsSourceIconClicked
    )
}

@Composable
fun AppMenuItem(
    modifier: Modifier = Modifier,
    @DrawableRes drawableResId: Int,
    @StringRes stringResId: Int,
    onClick: () -> Unit
) {
    IconButton(modifier = modifier, onClick = onClick) {
        Icon(
            painter = painterResource(id = drawableResId),
            contentDescription = stringResource(id = stringResId),
            tint = Color.Unspecified
        )
    }
}