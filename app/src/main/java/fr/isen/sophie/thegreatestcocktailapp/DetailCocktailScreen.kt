package fr.isen.sophie.thegreatestcocktailapp.fr.isen.sophie.thegreatestcocktailapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import fr.isen.sophie.thegreatestcocktailapp.ApiClient
import coil.compose.AsyncImage


fun Drink.ingredientsLines(): List<String> {
    val pairs = listOf(
        strIngredient1 to strMeasure1,
        strIngredient2 to strMeasure2,
        strIngredient3 to strMeasure3,
        strIngredient4 to strMeasure4,
        strIngredient5 to strMeasure5,
        strIngredient6 to strMeasure6,
        strIngredient7 to strMeasure7,
        strIngredient8 to strMeasure8,
        strIngredient9 to strMeasure9,
        strIngredient10 to strMeasure10,
        strIngredient11 to strMeasure11,
        strIngredient12 to strMeasure12,
        strIngredient13 to strMeasure13,
        strIngredient14 to strMeasure14,
        strIngredient15 to strMeasure15
    )

    return pairs
        .filter { (ing, _) -> !ing.isNullOrBlank() }
        .map { (ing, measure) ->
            val m = measure?.trim().orEmpty()
            if (m.isBlank()) "• ${ing!!.trim()}" else "• ${ing!!.trim()} — $m"
        }
}
@Composable
fun DetailCocktailScreen(
    drinkId: String? = null,
    onDrinkIdChanged: (String?) -> Unit = {}
) {
    var drink by remember { mutableStateOf<Drink?>(null) }

    LaunchedEffect(drinkId) {
        val response = if (drinkId.isNullOrBlank()) {
            ApiClient.apiService.getRandom()
        } else {
            ApiClient.apiService.getDrinkDetail(drinkId)
        }
        drink = response.drinks?.firstOrNull()
        onDrinkIdChanged(drink?.idDrink)
    }

    val d = drink ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        AsyncImage(
            model = d.strDrinkThumb,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
        )

        Spacer(Modifier.height(12.dp))
        Text(d.strDrink ?: "Unknown")

        Spacer(Modifier.height(12.dp))
        Text("Category: ${d.strCategory ?: "-"}")
        Text("Glass: ${d.strGlass ?: "-"}")

        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.9f)
            ),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("Ingredients")
                Spacer(Modifier.height(8.dp))
                d.ingredientsLines().forEach { Text(it) }
            }
        }

        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.9f)
            ),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("Recipe")
                Spacer(Modifier.height(8.dp))
                Text(d.strInstructions ?: "")
            }
        }
    }
}












//private fun Drink.getIngredientsWithMeasures(): List<String> {
  //  val pairs = listOf(
    //    strIngredient1 to strMeasure1,
    //    strIngredient2 to strMeasure2,
  //      strIngredient3 to strMeasure3,
  //      strIngredient4 to strMeasure4,
    //    strIngredient5 to strMeasure5,
     //   strIngredient6 to strMeasure6,
       // strIngredient7 to strMeasure7,
    //    strIngredient8 to strMeasure8,
      //  strIngredient9 to strMeasure9,
   //     strIngredient10 to strMeasure10,
     //   strIngredient11 to strMeasure11,
    //    strIngredient12 to strMeasure12,
      //  strIngredient13 to strMeasure13,
    //    strIngredient14 to strMeasure14,
      //  strIngredient15 to strMeasure15
   // )


//@Composable
//fun DetailCocktailScreen(drinkId: String? = null, catch: (Any?, () -> Unit?) -> Unit) {

  //  var drink by remember { mutableStateOf<Drink?>(null) }

  //  LaunchedEffect(drinkId) {
    //    val response = if (drinkId.isNullOrBlank()) {
      //      ApiClient.apiService.getRandom()   // ✅ ICI
     //   } else {
       //     ApiClient.apiService.getDrinkDetail(drinkId)  // ✅ OU ICI
     //   }

       // drink = response.drinks?.firstOrNull()
  //  }


    // charge 1 fois au démarrage
   // LaunchedEffect(Unit) {
      //  try {
       //     isLoading = true
        //    val response = ApiClient.apiService.getRandom()
      //      drink = response.drinks?.firstOrNull()
       //     error = null
     //   } catch (e: Exception) {
     //       error = e.message ?: "Erreur API"
    //    } finally {
    //        isLoading = false
    //    }
 //   }





//@Composable
//fun DetailCocktailScreen(modifier: Modifier = Modifier) {


  //  var drink by remember { mutableStateOf<Drink?>(null) }
    //var isLoading by remember { mutableStateOf(true) }
    //var error by remember { mutableStateOf<String?>(null) }

    // charge 1 fois au démarrage
    //LaunchedEffect(Unit) {
      //  try {
        //    isLoading = true
          //  val response = ApiClient.apiService.getRandom()
            //drink = response.drinks?.firstOrNull()
           // error = null
       // } catch (e: Exception) {
         //   error = e.message ?: "Erreur API"
       // } finally {
         //   isLoading = false
      //  }
   // }
   // Column(
     //   modifier = Modifier
         //   .fillMaxSize()
       //     .verticalScroll(rememberScrollState())
           // .padding(16.dp)
   // ) {

     //   Text("Welcome to the Greatest Cocktail App!")
       // Text("Discover amazing cocktails")

      //  Button(onClick = { Log.d("Button", "Button clicked") }) {
        //    Text("See cocktails")
     //   }

       // Image(
         //   painter = painterResource(id = R.drawable.image_cockatil_1),
           // contentDescription = "Cocktail image",
          //  modifier = Modifier
            //    .fillMaxWidth()
              //  .height(550.dp)
      //  )

     //   Text(text = "Dry Martini 🍸")

       // Spacer(modifier = Modifier.height(12.dp))

        // Categories + Glass
       // Text(text = "Categories: Classic • Strong • Aperitif")
      //  Spacer(modifier = Modifier.height(4.dp))
      //  Text(text = "Glass: Martini glass")

     //   Spacer(modifier = Modifier.height(16.dp))

        // Ingredients Card
     //   Card(modifier = Modifier.fillMaxWidth()) {
       //     Column(modifier = Modifier.padding(16.dp)) {
         //       Text(text = "Ingredients")
           //     Spacer(modifier = Modifier.height(8.dp))
             //   Text("• 60 ml Gin")
             //   Text("• 10 ml Dry Vermouth")
            //    Text("• Ice cubes")
              //  Text("• Lemon twist or olive")
         //   }
     //   }
       // Spacer(modifier = Modifier.height(16.dp))

        // Recipe Card
      //  Card(modifier = Modifier.fillMaxWidth()) {
        //    Column(modifier = Modifier.padding(16.dp)) {
          //      Text(text = "Recipe")
            //    Spacer(modifier = Modifier.height(8.dp))
              //  Text("1) Fill a mixing glass with ice.")
                //Text("2) Add gin and dry vermouth.")
         //       Text("3) Stir well until chilled.")
           //     Text("4) Strain into a chilled martini glass.")
          //      Text("5) Garnish with a lemon twist or olive.")
         //   }
      //  }
   // }
//}

