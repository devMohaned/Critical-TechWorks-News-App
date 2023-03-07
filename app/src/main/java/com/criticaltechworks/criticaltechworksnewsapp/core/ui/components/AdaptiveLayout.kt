package com.criticaltechworks.criticaltechworksnewsapp.core.ui.components

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


/**
 *
 * @param CompactLayout This is for mobile phone in portrait
 * @param MediumLayout this is for Tablet in portrait & Foldable in portrait (unfolded)
 * @param ExpandedLayout this is for Phone in landscape & Tablet in landscape & Foldable in landscape (unfolded) & Desktop
 * @param DefaultLayout this is for the other remaining cases
 *
 */
@Composable
fun HandleAdaptiveLayout(
    windowSize: WindowWidthSizeClass,
    CompactLayout: @Composable (modifier: Modifier) -> Unit,
    MediumLayout: @Composable (modifier: Modifier) -> Unit,
    ExpandedLayout: @Composable (modifier: Modifier) -> Unit,
    DefaultLayout: @Composable (modifier: Modifier) -> Unit
) {
    when (windowSize) {
        WindowWidthSizeClass.Compact -> CompactLayout(Modifier)
        WindowWidthSizeClass.Medium -> MediumLayout(Modifier)
        WindowWidthSizeClass.Expanded -> ExpandedLayout(Modifier)
        else -> DefaultLayout(Modifier)

    }
}