package com.bucanante.cryptohouse.screens.firebase

import com.bucanante.cryptohouse.screens.models.FirestoreCoin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

fun deleteCoin(coin: FirestoreCoin, uid: String?) {
    val db = FirebaseFirestore.getInstance()
    val coinRef = db.collection("coins")
        .whereEqualTo("uid", uid)
        .whereEqualTo("coinId", coin.id)

    coinRef.get().addOnSuccessListener { querySnapshot ->
        for (doc in querySnapshot.documents) {
            doc.reference.delete()
        }
    }
}

fun logoutUser() {
    val auth = FirebaseAuth.getInstance()
    auth.signOut()
}