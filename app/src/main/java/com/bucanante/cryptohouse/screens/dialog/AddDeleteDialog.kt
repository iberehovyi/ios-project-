package com.bucanante.cryptohouse.screens.dialog

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import com.bucanante.cryptohouse.screens.models.FirestoreCoin

@Composable
fun DeleteConfirmationDialog(
    coin: FirestoreCoin?,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (coin != null) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Delete Coin") },
            text = { Text(text = "Are you sure you want to delete ${coin.name}?") },
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("No")
                }
            }
        )
    }
}