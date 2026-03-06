package fr.isen.sophie.thegreatestcocktailapp

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.isen.sophie.thegreatestcocktailapp.fr.isen.sophie.thegreatestcocktailapp.ApiService
import fr.isen.sophie.thegreatestcocktailapp.ui.theme.SoftCuteOrange
import fr.isen.sophie.thegreatestcocktailapp.ui.theme.SoftOrange
import fr.isen.sophie.thegreatestcocktailapp.ui.theme.SoftPeach
import fr.isen.sophie.thegreatestcocktailapp.ui.theme.SoftPink
import fr.isen.sophie.thegreatestcocktailapp.ui.theme.SoftRose
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun CategoriesScreen(navController: NavController) {

    var categories by remember { mutableStateOf<List<String>>(emptyList()) }

    LaunchedEffect(Unit) {
        val response = ApiClient.apiService.getCategories()
        categories = response.drinks?.mapNotNull { it.strCategory } ?: emptyList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        SoftRose,
                        SoftPeach
                    )
                )
            )
            .padding(16.dp)
    ) {
        Text("Categories")
        Spacer(Modifier.height(12.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(categories) { category ->
                Button(
                    onClick = {
                        val safe = Uri.encode(category)
                        navController.navigate("drinks/$safe")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SoftPink
                    ),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = category,
                        color = Color.White
                    )
                }
            }
        }
    }
}




