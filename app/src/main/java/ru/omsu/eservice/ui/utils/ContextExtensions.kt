package ru.omsu.eservice.ui.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import ru.omsu.eservice.R
import ru.omsu.eservice.ui.MainActivity
import kotlin.random.Random

fun Context.getAnimation(@AnimRes res: Int): Animation = AnimationUtils.loadAnimation(this, res)

fun Context.color(resId: Int) = ContextCompat.getColor(this, resId)

fun Context.sendNotification(){
    val intent = Intent(this, MainActivity::class.java)
    intent.apply {
        flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
    }

    val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

    val builder = NotificationCompat.Builder(this, "e_service_channel_id")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("Новая оценка")
        .setContentText("Проверьте учебную карточку")
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(pendingIntent)

    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channelId = "e_service_channel_id"
        val channel = NotificationChannel(
            channelId,
            "EServices",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)
        builder.setChannelId(channelId)
    }

    notificationManager.notify(Random.nextInt(), builder.build())

}