package fr.isen.sophie.thegreatestcocktailapp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import fr.isen.sophie.thegreatestcocktailapp.fr.isen.sophie.thegreatestcocktailapp.Drink

@Composable
fun DrinksByCategoryScreen(navController: NavController, category: String) {

    var drinks by remember { mutableStateOf<List<Drink>>(emptyList()) }

    LaunchedEffect(category) {
        val response = ApiClient.apiService.getDrinksByCategory(category)
        drinks = response.drinks ?: emptyList()
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Category: $category")
        Spacer(Modifier.height(12.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(drinks) { d ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val id = d.idDrink ?: return@clickable
                            navController.navigate("detail/$id")
                        }
                        .padding(8.dp)
                ) {
                    AsyncImage(
                        model = d.strDrinkThumb,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(d.strDrink ?: "")
                }
            }
        }
    }
}
