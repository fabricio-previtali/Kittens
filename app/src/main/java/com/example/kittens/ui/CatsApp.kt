package com.example.kittens.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kittens.R
import com.example.kittens.ui.screens.CatDetailScreen
import com.example.kittens.ui.screens.CatUiState
import com.example.kittens.ui.screens.CatViewModel
import com.example.kittens.ui.screens.HomeScreen
import com.example.kittens.ui.theme.tertiaryContainerLight

enum class CatScreen {
    Home,
    Detail,
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatsApp(navController: NavHostController = rememberNavController()) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val catViewModel: CatViewModel = viewModel(factory = CatViewModel.Factory)
    val currentRoute =
        navController
            .currentBackStackEntryAsState()
            .value
            ?.destination
            ?.route

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CatsTopAppBar(
                scrollBehavior = scrollBehavior,
                navController = navController,
                showBackButton = currentRoute?.startsWith(CatScreen.Detail.name) == true,
            )
        },
    ) { paddingValues ->
        Surface(modifier = Modifier.fillMaxSize()) {
            NavHost(
                navController = navController,
                startDestination = CatScreen.Home.name,
                modifier = Modifier.padding(paddingValues),
            ) {
                composable(route = CatScreen.Home.name) {
                    HomeScreen(
                        catUiState = catViewModel.catUiState,
                        retryAction = catViewModel::getCat,
                        onCatClick = { cat ->
                            navController.navigate("${CatScreen.Detail.name}/${cat.name}")
                        },
                    )
                }
                composable(
                    route = "${CatScreen.Detail.name}/{catName}",
                    arguments = listOf(navArgument("catName") { type = NavType.StringType }),
                ) { backStackEntry ->
                    val catName = backStackEntry.arguments?.getString("catName")
                    val selectedCat =
                        (catViewModel.catUiState as? CatUiState.Success)
                            ?.cats
                            ?.find { it.name == catName }
                    if (selectedCat != null) {
                        CatDetailScreen(
                            cat = selectedCat,
                            modifier = Modifier,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatsTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavHostController?,
    modifier: Modifier = Modifier,
    showBackButton: Boolean = false,
) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                modifier = Modifier,
                verticalAlignment = CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cat_fingerprint),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.cat_fingerprint),
                    contentDescription = null,
                )
            }
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = {
                    navController?.navigateUp()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior,
        modifier =
            modifier
                .statusBarsPadding(),
        colors =
            TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = tertiaryContainerLight,
            ),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun CatsTopAppBarPreviewShowBackButton() {
    CatsTopAppBar(
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
        navController = null,
        showBackButton = true,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun CatsTopAppBarPreview() {
    CatsTopAppBar(
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
        navController = null,
        showBackButton = false,
    )
}
