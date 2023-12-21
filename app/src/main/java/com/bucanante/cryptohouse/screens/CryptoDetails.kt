package com.bucanante.cryptohouse.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

@Composable
fun CryptoDetailsScreen(coinId: String, coinName: String, coinPrice: String, coinImage: String) {
    val context = LocalContext.current
    var quantity by remember { mutableStateOf("") }
    var purchasePrice by remember { mutableStateOf("") }

    if (coinId.isNotEmpty()) {
        Column {
            CryptoOnScreen(
                coinId = coinId,
                coinName = coinName,
                coinPrice = coinPrice,
                coinImage = coinImage,
                quantity = quantity,
                onQuantityChanged = { quantity = it },
                purchasePrice = purchasePrice,
                onPurchasePriceChanged = { purchasePrice = it }
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 10.dp),
                onClick = {
                    val uid = FirebaseAuth.getInstance().currentUser?.uid
                    if (uid != null) {
                        val coin = hashMapOf(
                            "coinId" to coinId,
                            "coinName" to coinName,
                            "coinPrice" to coinPrice.toDouble(),
                            "coinImage" to coinImage,
                            "uid" to uid,
                            "quantity" to quantity.toDouble(),
                            "purchasePrice" to purchasePrice.toDouble()
                        )

                        FirebaseFirestore.getInstance()
                            .collection("coins")
                            .document(UUID.randomUUID().toString())
                            .set(coin)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    context,
                                    "Successfully added to your portfolio.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(
                                    context,
                                    "Something went wrong. Try again.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                }
            ) {
                Text(
                    text = "SAVE",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    } else {
        // Handle the case when the coinId is not available
    }
}

@Composable
fun CryptoOnScreen(
    coinId: String,
    coinName: String,
    coinPrice: String,
    coinImage: String,
    quantity: String,
    onQuantityChanged: (String) -> Unit,
    purchasePrice: String,
    onPurchasePriceChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = 20.dp)
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = 4.dp,
            modifier = Modifier.padding(8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f) // Add weight here to fill the available space
                    ) {
                        Text(
                            text = coinName,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Light,
                            modifier = Modifier.padding(bottom = 8.dp)

                        )
                        Text(
                            text = coinId.uppercase(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)

                        )
                        Text(
                            text = "Price: $${coinPrice}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Spacer(Modifier.width(8.dp)) // Add a Spacer for some extra space between the Column and Image
                    Image(
                        painter = rememberImagePainter(coinImage),
                        contentDescription = coinName,
                        modifier = Modifier
                            .size(80.dp)
                            .padding(end = 10.dp),
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp)) // Add some spacing between the Row and OutlinedTextField's Row
        Row(
            modifier = Modifier.fillMaxWidth(),
           // horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
              //  verticalArrangement = Arrangement.Center,
              //  horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                OutlinedTextField(
                    value = quantity,
                    onValueChange = onQuantityChanged,
                    label = { Text("Quantity") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        //.height(10.dp)
                )
            }
            Column(
              //  verticalArrangement = Arrangement.Center,
               // horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                OutlinedTextField(
                    value = purchasePrice,
                    onValueChange = onPurchasePriceChanged,
                    label = { Text("Purchase Price") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun CryptoOnScreenPreview() {
    CryptoOnScreen(
        coinId = "BTC",
        coinName = "Bitcoin",
        coinPrice = "50,000",
        coinImage = "https://www.example.com/bitcoin.png",
        quantity = "1.0",
        onQuantityChanged = {},
        purchasePrice = "45,000",
        onPurchasePriceChanged = {}
    )
}




