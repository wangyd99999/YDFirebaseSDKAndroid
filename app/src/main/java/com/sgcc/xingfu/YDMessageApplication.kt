package com.sgcc.xingfu

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.sgcc.ydfirebasesdk.YDFirebaseSDK

class YDMessageApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        YDFirebaseSDK.singleInstance.registerNotification(this)
    }

}