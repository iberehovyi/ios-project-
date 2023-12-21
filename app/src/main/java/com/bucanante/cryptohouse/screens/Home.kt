package com.bucanante.cryptohouse.screens

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bucanante.cryptohouse.navigation.BottomBarScreen
import android.content.Intent
import android.widget.Toast
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.bucanante.cryptohouse.screens.apiService.CoinGeckoApiService
import com.bucanante.cryptohouse.screens.models.Coin
import com.bucanante.cryptohouse.screens.utils.SearchBox
import com.bucanante.cryptohouse.screens.utils.shareOnTwitter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException

@Composable
fun HomeScreen(navController: NavHostController) {
    CoinList(navController)
}

@Composable
fun CoinList(navController: NavHostController) {
    val coinsAPI = remember { mutableStateOf<List<Coin>>(emptyList()) }
    var searchTerm by remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(true) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/api/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(CoinGeckoApiService::class.java)
        try {
            delay(1000) // Add a delay of 1 second (1000 milliseconds) before making the API call
            val coins = apiService.getCoins()
            coinsAPI.value = coins
        } catch (e: HttpException) {
            if (e.code() == 429) {
                Toast.makeText(context, "Error: Rate limit exceeded", Toast.LENGTH_LONG).show()
            }
        } finally {
            isLoading.value = false
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading.value) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            Column {
                SearchBox(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
                    onValueChanged = { newSearchTerm ->
                        searchTerm = newSearchTerm
                    }
                )

                Text(
                    text = "Trending Coins - 24h",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                )

                TrendingCoins()

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "All Coins",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, bottom = 20.dp)
                )

                LazyColumn {
                    items(coinsAPI.value) { coin ->
                        if (coin.name.contains(searchTerm, ignoreCase = true) ||
                            coin.symbol.contains(searchTerm, ignoreCase = true)
                        ) {
                            CryptoBoxCard(coin, navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CryptoBoxCard(coin: Coin, navController: NavHostController) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                navController.navigate(
                    BottomBarScreen.CryptoDetails.createRoute(
                        coinId = coin.id,
                        coinName = coin.name,
                        coinPrice = coin.current_price.toString(),
                        coinImage = coin.image
                    )
                )
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f) // Add weight here to fill the available space
            ) {
                Text(text = coin.name)
                Text(text = coin.symbol.uppercase())
                Text(text = "Price: $${coin.current_price}")
            }
            Spacer(Modifier.width(8.dp)) // Add a Spacer for some extra space between the Column and Image
            Image(
                painter = rememberImagePainter(coin.image),
                contentDescription = coin.name,
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 10.dp)
            )
        }
    }
}

@Composable
fun TrendingCoins() {
    val trendingCoinsAPI = remember { mutableStateOf<List<Coin>>(emptyList()) }

    LaunchedEffect(Unit) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/api/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(CoinGeckoApiService::class.java)

        val trendingCoins = apiService.getTrendingCoins()
        trendingCoinsAPI.value = trendingCoins.coins.map { it.item }
    }

    LazyRow(
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(trendingCoinsAPI.value) { coin ->
            val index = trendingCoinsAPI.value.indexOf(coin)
            TrendingCoinCard(coin, index)
        }
    }
}

@Composable
fun TrendingCoinCard(coin: Coin, index: Int) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    Surface(
        modifier = Modifier
            .padding(8.dp)
            .width(120.dp)
            .clickable {
                coroutineScope.launch {
                    shareOnTwitter(context, coin)
                }
            },
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colors.surface,
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "Arrow Up Icon",
                tint = Color.Green,
                modifier = Modifier.size(50.dp)
            )

            Text(
                text = "${index+1}",
                fontWeight = FontWeight.Bold,
                fontSize = 50.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            )
        }
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Spacer(modifier = Modifier.height(78.dp))
            Text(text = coin.name, textAlign = TextAlign.Center)
            Text(text = coin.symbol.uppercase(), textAlign = TextAlign.Center)
        }
        Box(modifier = Modifier.size(30.dp).padding(10.dp)) {
            Icon(Icons.Filled.Share, contentDescription = "Share")
        }
    }
}

@Preview
@Composable
fun TrendingCoinCardPreview() {
    val coin = Coin("Bitcoin", "BTC", name = "Bitcoin", image = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579", current_price = 3500.05)
    TrendingCoinCard(coin = coin, index = 0)
}
