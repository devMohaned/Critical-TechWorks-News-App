package com.criticaltechworks.criticaltechworksnewsapp.core.ui.navigation


import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.criticaltechworks.criticaltechworksnewsapp.feature_fingerprint.ui.FingerPrintScreen
import com.criticaltechworks.criticaltechworksnewsapp.feature_fingerprint.ui.FingerPrintViewModel
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.ui.news_details.NewsDetailScreen
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.ui.news_details.NewsDetailsLinking
import com.criticaltechworks.criticaltechworksnewsapp.feature_news.ui.news_list.NewsScreen


@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    windowSize: WindowWidthSizeClass,
) {
    val navController: NavHostController = rememberNavController()
    val viewModel: FingerPrintViewModel = hiltViewModel<FingerPrintViewModel>()

    val startDestination =
        if (viewModel.canUseFingerPrint(navController.context)) NavigationGraph.FingerPrint.name
        else NavigationGraph.NewsList.name

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    )
    {

        composable(route = NavigationGraph.FingerPrint.name)
        {
            FingerPrintScreen(windowSize = windowSize, onNewsListNavigation = {
                navController.navigate(NavigationGraph.NewsList.name){
                    popUpTo(NavigationGraph.FingerPrint.name) {
                        inclusive = true
                    }
                }
        })
        }


        composable(route = NavigationGraph.NewsList.name)
        {
            NewsScreen(windowSize = windowSize, onNewsDetailNavigation = { article ->
                navController.navigate(NavigationGraph.NewsDetails.name + "/${article.title}/${article.publishedAt}")
            })
        }

        composable(
            route = "${NavigationGraph.NewsDetails.name}/{${NewsDetailsLinking.ARG_1_TITLE}}/{${NewsDetailsLinking.ARG_2_PUBLISH_AT}}",
            arguments = listOf(
                navArgument(
                    name = "${NewsDetailsLinking.ARG_1_TITLE}",
                    builder = {
                        defaultValue = "Default"
                        type = NavType.StringType
                        nullable = true
                    }),
                navArgument(
                    name = "${NewsDetailsLinking.ARG_2_PUBLISH_AT}",
                    builder = {
                        defaultValue = "Default"
                        type = NavType.StringType
                        nullable = true
                    },
                )
            )
        )
        {
            val title = it.arguments?.getString(NewsDetailsLinking.ARG_1_TITLE)!!
            val publishedAt = it.arguments?.getString(NewsDetailsLinking.ARG_2_PUBLISH_AT)!!

            NewsDetailScreen(windowSize = windowSize,title = title, publishedAt = publishedAt, onBackButtonPressed = {
                navController.navigateUp()
            })
        }
    }
}