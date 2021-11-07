package com.fruktoland.app.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FruktoLandFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val b = 0
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        val b = 0
    }
}