//package com.aco.usedoilcollection.ui
//
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.Tab
//import androidx.compose.material3.TabRow
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalSoftwareKeyboardController
//import androidx.compose.ui.platform.testTag
//import androidx.navigation.NavHostController
//import com.aco.usedoilcollection.R
//import com.aco.usedoilcollection.fragments.InputFragment
//import com.aco.usedoilcollection.fragments.StatisticsFragment
//import com.aco.usedoilcollection.fragments.AccountFragment
//import com.aco.usedoilcollection.fragments.LocationsFragment
//import com.aco.usedoilcollection.viewmodel.OilCollectionViewModel
//import com.google.accompanist.pager.ExperimentalPagerApi
//import com.google.accompanist.pager.HorizontalPager
//import com.google.accompanist.pager.rememberPagerState
//import kotlinx.coroutines.launch
//import com.aco.usedoilcollection.viewmodel.AuthViewModel
//import com.aco.usedoilcollection.viewmodel.LocationViewModel
//
//
//@OptIn(ExperimentalPagerApi::class)
//@Composable
//fun ViewPagerScreen(
//    modifier: Modifier = Modifier,
//    remainingVolume: Int,
//    onAddLiters: (Int, Int) -> Unit,
//    oilCollectionViewModel: OilCollectionViewModel,
//    authViewModel: AuthViewModel,
//    locationViewModel: LocationViewModel,
//    navController: NavHostController
//) {
//    val pagerState = rememberPagerState()
//    val coroutineScope = rememberCoroutineScope()
//    val keyboardController = LocalSoftwareKeyboardController.current
//
//    Column(modifier = modifier.fillMaxSize()) {
//        TabRow(selectedTabIndex = pagerState.currentPage) {
//            Tab(
//                text = { Text("Input") },
//                selected = pagerState.currentPage == 0,
//                onClick = {
//                    coroutineScope.launch {
//                        pagerState.scrollToPage(0)
//                    }
//                }
//            )
//            Tab(
//                text = { Text("Statistics") },
//                selected = pagerState.currentPage == 1,
//                onClick = {
//                    coroutineScope.launch {
//                        pagerState.scrollToPage(1)
//                        keyboardController?.hide()
//                    }
//                }
//            )
//            Tab(
//                text = { Text("Locations") },
//                selected = pagerState.currentPage == 2,
//                onClick = {
//                    coroutineScope.launch {
//                        pagerState.scrollToPage(2)
//                        keyboardController?.hide()
//                    }
//                }
//            )
//            Tab(
//                text = { Text("Account") },
//                selected = pagerState.currentPage == 3,
//                onClick = {
//                    coroutineScope.launch {
//                        pagerState.scrollToPage(3)
//                        keyboardController?.hide()
//                    }
//                }
//            )
//        }
//        HorizontalPager(count = 4, state = pagerState) { page ->
//            when (page) {
//                0 -> InputFragment(remainingVolume = remainingVolume, onAddLiters = onAddLiters, locationViewModel = locationViewModel)
//                1 -> {
//                    keyboardController?.hide()
//                    StatisticsFragment(viewModel = oilCollectionViewModel)
//                }
//                2 -> LocationsFragment(viewModel = locationViewModel)
//                3 -> {
//                    keyboardController?.hide()
//                    AccountFragment(authViewModel = authViewModel, navController = navController)
//                }
//            }
//        }
//    }
//}
