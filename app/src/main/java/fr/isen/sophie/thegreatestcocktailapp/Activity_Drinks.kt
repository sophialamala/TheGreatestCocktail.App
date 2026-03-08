package fr.isen.sophie.thegreatestcocktailapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.isen.sophie.thegreatestcocktailapp.ui.theme.TheGreatestCocktailAppTheme

class Activity_Drinks : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val category = intent.getStringExtra("category") ?: "Unknown"

        setContent {
            TheGreatestCocktailAppTheme {
                DrinksScreen(category = category)
            }
        }
    }
}

@Composable
fun DrinksScreen(category: String) {
    val drinks = when (category) {
        "Beer" -> listOf(
            "Heineken", "Corona", "Guinness", "Desperados",
            "Leffe Blonde", "Grimbergen", "1664", "Budweiser"
        )
        else -> listOf("No drinks available for $category")
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            item {
                Text(
                    text = "Category: $category",
                    modifier = Modifier.padding(16.dp)
                )
            }

            items(drinks) { drink ->
                Text(
                    text = drink,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}