package com.criticaltechworks.criticaltechworksnewsapp.feature_news.ui.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.criticaltechworks.criticaltechworksnewsapp.R
import com.criticaltechworks.criticaltechworksnewsapp.core.ui.theme.*
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.domain.models.Source


/* TODO: It's possible to have all sources from Endpoint: https://newsapi.org/docs/endpoints/sources
 ** But that does not make sense to use it, as I don't know the full requirements, so hard-coded for now. */
@Composable
fun NewsSourcesAlertDialog(
    modifier: Modifier = Modifier,
    sources: Array<Source>,
    onNewsSourceSelected: (source: Source) -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(modifier = modifier, onDismissRequest = onDismissRequest, title = {
        AlertDialogTitle(title = stringResource(id = R.string.choose_a_source))
    }, text = {
      /* TODO: AlertDialog does not support scrolling children, thus it has weird stutter effect when scrolling
      *   keep it as it is, change it later. */
        LazyColumn(modifier = modifier.height(DIALOG_HEIGHT)) {
            items(sources.size)
            {
                Source(sourceName = sources[it].name, onSourecClicked = {
                    onNewsSourceSelected(sources[it])
                    onDismissRequest()
                })
            }
        }
    }, buttons = {
        OutlinedButton(
            modifier = modifier
                .padding(horizontal = SPACING_DOUBLE, vertical = SPACING_DOUBLE)
                .fillMaxWidth()
                .clip(CornerButtonShape),
            onClick = onDismissRequest
        ) {
            Text(text = stringResource(id = R.string.dismiss))
        }
    })
}


@Composable
fun AlertDialogTitle(modifier: Modifier = Modifier, title: String) {
    Text(
        modifier = modifier,
        text = title,
        style = MaterialTheme.typography.h3
    )
}

@Composable
fun Source(modifier: Modifier = Modifier, sourceName: String, onSourecClicked: () -> Unit) {
    NormalButton(
        modifier = modifier
            .padding(horizontal = SPACING_DOUBLE)
            .padding(bottom = SPACING_NORMAL)
            .fillMaxWidth(), onClick = onSourecClicked
    ) {
        Text(text = sourceName)
    }
}