package com.example.cmcinterviewtask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cmcinterviewtask.presentation.coin_detail.CoinDetailViewModel
import com.example.cmcinterviewtask.presentation.coin_detail.CoinDetailViewModelFactory
import com.example.cmcinterviewtask.presentation.coin_detail.components.CoinDetailScreen
import com.example.cmcinterviewtask.presentation.coin_list.CoinListViewModelFactory
import com.example.cmcinterviewtask.presentation.common.AppTopBar
import com.example.cmcinterviewtask.presentation.ui.theme.CMCInterviewTaskTheme
import com.example.democleanarchitecture.presentation.coin_list.CoinListViewModel
import com.example.democleanarchitecture.presentation.coin_list.components.CoinListScreen


class MainActivity : ComponentActivity() {

    private val coinListViewModel: CoinListViewModel by viewModels {
        val app = application as MainApplication
        CoinListViewModelFactory(app.container.getCoinsUseCase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CMCInterviewTaskTheme {
                val navController = rememberNavController()
                val canNavigateBack = navController.previousBackStackEntry != null
                Scaffold(modifier = Modifier.fillMaxSize(),

                    topBar = {
                        AppTopBar(
                            canNavigateBack = canNavigateBack,
                            onBackClick = { navController.popBackStack() }
                        )
                    }) { innerPadding ->


                    NavHost(
                        navController = navController,
                        startDestination = Screen.CoinListScreen.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {

                        composable(
                            route = Screen.CoinListScreen.route
                        ) {
                            CoinListScreen(
                                navController = navController,
                                viewModel = coinListViewModel
                            )
                        }




                        composable(
                            route = Screen.CoinDetailScreen.route,
                            arguments = listOf(
                                navArgument("id") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->

                            val id = backStackEntry.arguments?.getString("id")
                                ?: error("id missing")

                            val app = LocalContext.current.applicationContext as MainApplication

                            val coinDetailViewModel: CoinDetailViewModel = viewModel(
                                viewModelStoreOwner = backStackEntry,
                                factory = CoinDetailViewModelFactory(
                                    id = id,
                                    getCoinUseCase = app.container.getCoinUseCase
                                )
                            )

                            CoinDetailScreen(coinDetailViewModel)

                        }





                    }
                }
            }


        }
    }


}
