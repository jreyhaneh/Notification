package com.example.notification

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    val ChannelId = "ChannelId"
    val ChannelName = "ChannelName"
    val notificatinId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.but_notif)

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        val pedingIntent= TaskStackBuilder.create(this).run {
//            addNextIntentWithParentStack(intent)
//            getPendingIntent(0,PendingIntent.FLAG_IMMUTABLE)
//        }
        val pedingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_ONE_SHOT)
        val notificatinManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                ChannelName,
                ChannelId,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                lightColor = Color.GREEN
                enableLights(true)
            }
            notificatinManager.createNotificationChannel(channel)
        }
        val notification =
            NotificationCompat.Builder(this, ChannelId)
                .setContentText("NOTIFICATION")
                .setContentText("this is a test")
                .setSmallIcon(R.drawable.ic_notif)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setVisibility(VISIBILITY_PUBLIC)
                .setContentIntent(pedingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .build()

//        val notificatinManager= NotificationManagerCompat.from(this)


        button.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                        1000
                    )
                }
            } else{
                val id = System.currentTimeMillis().toInt()
                notificatinManager.notify(id, notification)
            }
        }
    }

//    fun creatNotificationChannel(
//        notificationManager: NotificationManager
//    ) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//
//            val channel = NotificationChannel(
//                ChannelName,
//                ChannelId,
//                NotificationManager.IMPORTANCE_DEFAULT
//            ).apply {
//                lightColor = Color.GREEN
//                enableLights(true)
//            }
//            notificationManager.createNotificationChannel(channel)
//        }
//    }

}