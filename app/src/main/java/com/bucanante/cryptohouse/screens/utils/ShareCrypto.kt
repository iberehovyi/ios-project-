package com.bucanante.cryptohouse.screens.utils

import android.content.Context
import android.content.Intent
import com.bucanante.cryptohouse.screens.models.Coin

fun shareOnTwitter(context: Context, coin: Coin) {
    val shareText = "Check out this trending coin: ${coin.name} (${coin.symbol.uppercase()})! #cryptocurrency"

    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, shareText)
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}