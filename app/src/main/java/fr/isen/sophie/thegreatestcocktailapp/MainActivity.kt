package fr.isen.sophie.thegreatestcocktailapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import fr.isen.sophie.thegreatestcocktailapp.fr.isen.sophie.thegreatestcocktailapp.DetailCocktailScreen
import fr.isen.sophie.thegreatestcocktailapp.fr.isen.sophie.thegreatestcocktailapp.FavoriteManager
import fr.isen.sophie.thegreatestcocktailapp.ui.theme.TheGreatestCocktailAppTheme
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import fr.isen.sophie.thegreatestcocktailapp.ui.theme.SoftPeach
import fr.isen.sophie.thegreatestcocktailapp.ui.theme.SoftPink


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TheGreatestCocktailAppTheme {

                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route ?: "random"

                val context = LocalContext.current


                var currentDrinkId by remember { mutableStateOf<String?>(null) }


                var isFav by remember { mutableStateOf(false) }


                fun updateCurrentDrinkId(id: String?) {
                    currentDrinkId = id
                    isFav = if (id.isNullOrBlank()) false else FavoriteManager.isFavorite(context, id)
                }


                val showHeart =
                    currentRoute == "random" ||
                            currentRoute == "favorites" ||
                            currentRoute.startsWith("drinks/") ||
                            currentRoute.startsWith("detail/")

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(SoftPink, SoftPeach)
                            )
                        ),
                    containerColor = Color.Transparent,
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    when {
                                        currentRoute == "random" -> "Random"
                                        currentRoute == "list" -> "List"
                                        currentRoute == "favorites" -> "Favorites"
                                        currentRoute.startsWith("drinks/") -> "Drinks"
                                        currentRoute.startsWith("detail/") -> "Detail"
                                        else -> "Cocktail App"
                                    }
                                )
                            },

                            navigationIcon = {
                                if (currentRoute != "random") {
                                    IconButton(onClick = {
                                        navController.popBackStack()
                                    }) {
                                        Icon(
                                            imageVector = Icons.Filled.ArrowBack,
                                            contentDescription = "Back"
                                        )
                                    }
                                }
                            },
                            actions = {
                                if (!showHeart) return@TopAppBar

                                when {

                                    (currentRoute == "random" || currentRoute.startsWith("detail/")) &&
                                            !currentDrinkId.isNullOrBlank() -> {
                                        IconButton(
                                            onClick = {
                                                val id = currentDrinkId ?: return@IconButton
                                                FavoriteManager.toggle(context, id)
                                                isFav = FavoriteManager.isFavorite(context, id)
                                            }
                                        ) {
                                            Icon(
                                                imageVector = if (isFav) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                                contentDescription = "Favorite"
                                            )
                                        }
                                    }


                                    currentRoute == "favorites" -> {
                                        IconButton(onClick = { /* rien */ }) {
                                            Icon(
                                                imageVector = Icons.Filled.Favorite,
                                                contentDescription = "Favorites"
                                            )
                                        }
                                    }


                                    currentRoute.startsWith("drinks/") -> {
                                        IconButton(onClick = { navController.navigate("favorites") }) {
                                            Icon(
                                                imageVector = Icons.Filled.FavoriteBorder,
                                                contentDescription = "Go to favorites"
                                            )
                                        }
                                    }
                                }
                            }
                        )
                    },
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = currentRoute == "random",
                                onClick = {
                                    navController.navigate("random") {
                                        popUpTo("random") { inclusive = false }
                                        launchSingleTop = true
                                    }
                                },
                                icon = { Icon(Icons.Filled.Home, contentDescription = "Random") },
                                label = { Text("Random") }
                            )

                            NavigationBarItem(
                                selected = currentRoute == "list",
                                onClick = {
                                    navController.navigate("list") {
                                        popUpTo("random") { inclusive = false }
                                        launchSingleTop = true
                                    }
                                },
                                icon = { Icon(Icons.Filled.List, contentDescription = "List") },
                                label = { Text("List") }
                            )

                            NavigationBarItem(
                                selected = currentRoute == "favorites",
                                onClick = {
                                    navController.navigate("favorites") {
                                        popUpTo("random") { inclusive = false }
                                        launchSingleTop = true
                                    }
                                },
                                icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favorites") },
                                label = { Text("Favorites") }
                            )
                        }
                    }
                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = "random",
                        modifier = Modifier.padding(innerPadding)
                    ) {

                        // ✅ Random : coeur actif (on remonte l'id)
                        composable("random") {
                            DetailCocktailScreen(
                                drinkId = null,
                                onDrinkIdChanged = { updateCurrentDrinkId(it) }
                            )
                        }

                        composable("list") {
                            CategoriesScreen(navController = navController)
                        }

                        composable(
                            route = "drinks/{category}",
                            arguments = listOf(navArgument("category") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val category = backStackEntry.arguments?.getString("category") ?: ""
                            DrinksByCategoryScreen(
                                navController = navController,
                                category = category
                            )
                        }


                        composable(
                            route = "detail/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id") ?: ""
                            DetailCocktailScreen(
                                drinkId = id,
                                onDrinkIdChanged = { updateCurrentDrinkId(it) }
                            )
                        }

                        composable("favorites") {
                            FavoritesScreen(navController)
                        }
                    }
                }
            }
        }
    }
}
















               // Scaffold(
                  //  modifier = Modifier.fillMaxSize(),
                  //  topBar = {
                     //   TopAppBar(
                          //  title = { Text("Dry Martini 🍸") },
                          //  actions = {
                              //  IconButton(
                                  //  onClick = {
                                      //  Toast.makeText(
                                        //    context,
                                         //   "Added to favorites ❤️",
                                     //       Toast.LENGTH_SHORT
                                    //    ).show()
                                //    }
                             //   ) {
                                //    Icon(
                                     //   imageVector = Icons.Filled.Favorite,
                                      //  contentDescription = "Favorite"
                                 //   )
                             //   }
                         //   }
                     //   )
                //    }
            //    ) { innerPadding ->
                   // }



                //CategoriesScreen(
                   // modifier = Modifier.padding(innerPadding),
                   // onCategoryClick = { category ->

                       // val intent = Intent(context, Activity_Drinks::class.java)
                       // intent.putExtra("category", category)
                       // context.startActivity(intent)
                  //  }
               // )
                    //DetailCocktailScreen(modifier = Modifier.padding(innerPadding),


               // }
           // }
       // }
   // }
//}
