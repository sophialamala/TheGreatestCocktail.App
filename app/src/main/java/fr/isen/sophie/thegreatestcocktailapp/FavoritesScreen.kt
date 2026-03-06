package fr.isen.sophie.thegreatestcocktailapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.isen.sophie.thegreatestcocktailapp.fr.isen.sophie.thegreatestcocktailapp.FavoriteManager

@Composable
fun FavoritesScreen(navController: NavController) {

    val context = LocalContext.current
    var favIds by remember { mutableStateOf<List<String>>(emptyList()) }
    var namesById by remember { mutableStateOf<Map<String, String>>(emptyMap()) }

    fun refreshFavorites() {
        favIds = FavoriteManager.getIds(context)
    }

    LaunchedEffect(Unit) {
        refreshFavorites()
    }


    LaunchedEffect(favIds) {
        val map = mutableMapOf<String, String>()
        for (id in favIds) {
            try {
                val response = ApiClient.apiService.getDrinkDetail(id)
                val drink = response.drinks?.firstOrNull()
                map[id] = drink?.strDrink ?: "Unknown"
            } catch (e: Exception) {
                map[id] = "Error"
            }
        }
        namesById = map
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Favorites")
        Spacer(Modifier.height(12.dp))

        if (favIds.isEmpty()) {
            Text("No favorites yet")
            return
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(favIds, key = { it }) { id ->
                val name = namesById[id] ?: "Loading..."

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.9f)
                    ),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navController.navigate("detail/$id") }
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text(text = name)
                            Spacer(Modifier.height(4.dp))
                            Text(text = "id: $id")
                        }

                        IconButton(
                            onClick = {

                                FavoriteManager.toggle(context, id)

                                // ✅ refresh la liste (et retire direct de l'écran)
                                refreshFavorites()

                                // (optionnel) nettoyer le cache des noms
                                namesById = namesById - id
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Remove from favorites"
                            )
                        }
                    }
                }
            }
        }
    }
}