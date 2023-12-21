package com.bucanante.cryptohouse.screens

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.bucanante.cryptohouse.R
import com.bucanante.cryptohouse.screens.dialog.DeleteConfirmationDialog
import com.bucanante.cryptohouse.screens.firebase.deleteCoin
import com.bucanante.cryptohouse.screens.models.FirestoreCoin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun PortfolioScreen() {
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    val coinsState = remember { mutableStateOf<List<FirestoreCoin>>(emptyList()) }

    val showDialog = remember { mutableStateOf(false) }
    val coinToDelete = remember { mutableStateOf<FirestoreCoin?>(null) }

    val loading = remember { mutableStateOf(true) }

    LaunchedEffect(uid) {
        FirebaseFirestore.getInstance().collection("coins")
            .whereEqualTo("uid", uid)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    // Handle error
                    return@addSnapshotListener
                }
                if (value != null) {
                    val coinsList = mutableListOf<FirestoreCoin>()
                    for (doc in value.documents) {
                        val coin = FirestoreCoin(
                            id = doc["coinId"] as String,
                            name = doc["coinName"] as String,
                            symbol = "",
                            image = doc["coinImage"] as String,
                            current_price = doc["coinPrice"] as Double,
                            quantity = doc["quantity"] as Double,
                            purchasePrice = doc["purchasePrice"] as Double
                        )
                        coinsList.add(coin)
                    }
                    coinsState.value = coinsList
                    println("${coinsState.value}")
                }
                loading.value = false
            }
    }
    Column {
        if (loading.value) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            // The way I calculate return is I prompt user to enter the price he purchased coins at
            // And I also retrieve the current price of the coin
            // I subtract that current price and purchase price and multiply it by coin quantity

            // PORTFOLIO RETURN
            val portfolioReturn = coinsState.value.fold(0.0) { acc, coin ->
                acc + (coin.current_price - coin.purchasePrice) * coin.quantity
            }
            val portfolioReturnTextColor = if (portfolioReturn >= 0) Color.Green else Color.Red
            // TOTAL AMOUNT SPENT
            val currentTotalValue = coinsState.value.fold(0.0) { acc, coin ->
                acc + (coin.current_price * coin.quantity)
            }
            val currentTotalTextColor = if (currentTotalValue >= 0) Color.Green else Color.Red

            PortfolioCalculatorCard(
                portfolioReturn = portfolioReturn,
                totalSpent = currentTotalValue,
                portfolioColor = portfolioReturnTextColor,
                totalColor = currentTotalTextColor
            )
            if (coinsState.value.isNotEmpty()) {
                for (coin in coinsState.value) {
                    PortfolioBoxCard(
                        coin = coin,
                        onDelete = {
                            coinToDelete.value = coin
                            showDialog.value = true
                        }
                    )

                }
            } else {
                Log.d("PortfolioScreen", "Coins is empty")
                Text(
                    text = "You haven't saved any coins yet.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    color = Color.Gray
                )
            }
            if (showDialog.value) {
                DeleteConfirmationDialog(
                    coin = coinToDelete.value,
                    onConfirm = {
                        deleteCoin(coinToDelete.value!!, uid)
                        showDialog.value = false
                    },
                    onDismiss = { showDialog.value = false }
                )
            }
        }
    }
}

@Composable
fun PortfolioBoxCard(coin: FirestoreCoin, onDelete: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onDelete)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)

        ) {
            Image(
                painter = rememberImagePainter(coin.image),
                contentDescription = coin.name,
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 15.dp)
            )
            Column(
                modifier = Modifier.weight(1f) // Add weight here to fill the available space
            ) {
                Text(
                    text = coin.id.uppercase(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Text(
                    text = "${coin.quantity} coins",
                    fontWeight = FontWeight.Light,
                    fontSize = 16.sp
                )
                Text(
                    text = "Cost: $${String.format("%.2f", coin.purchasePrice * coin.quantity)}",
                    fontWeight = FontWeight.Light,
                    fontSize = 16.sp
                )
            }
            Text(
                text = "$${String.format("%.2f", coin.current_price * coin.quantity)}",
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
            Image(
                imageVector = Icons.Default.Delete,
                contentDescription = "Logo",
                modifier = Modifier
                    .size(20.dp)
            )
        }
    }
}

@Composable
fun PortfolioCalculatorCard(portfolioReturn: Double, totalSpent: Double, portfolioColor: Color, totalColor: Color) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "YOUR PORTFOLIO RETURN",
                    fontWeight = FontWeight.Light,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.padding(end = 10.dp))
                Image(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(20.dp)
                )
            }
            Text(
                text = "$${String.format("%.2f", portfolioReturn)}",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = portfolioColor
            )
            Spacer(modifier = Modifier.padding(bottom = 20.dp))
            Card(
                shape = RoundedCornerShape(8.dp),
                elevation = 4.dp,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .border(1.dp, Color(R.color.purple_500), shape = RoundedCornerShape(8.dp))
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "TOTAL AMOUNT SPENT",
                        fontWeight = FontWeight.Light,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.padding(end = 60.dp))
                    Text(
                        text = "$${String.format("%.2f", totalSpent)}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = totalColor
                    )
                }
            } // card
        }
    }
}

@Preview
@Composable
fun PortfolioScreenPreview() {
    PortfolioCalculatorCard(portfolioReturn = 200.05, totalSpent = 2000.05, portfolioColor = Color.Green, totalColor = Color.Green)
}



