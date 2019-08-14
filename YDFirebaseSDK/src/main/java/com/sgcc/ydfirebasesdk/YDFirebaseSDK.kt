package com.sgcc.ydfirebasesdk

import android.app.Activity
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging


class YDFirebaseSDK {

    /** 保存Firebase注册返回Token */
    var firebaseToken: String? = null

    /** 保存上传Firebase注册的Token的URL */
    var baseURL: String? = null

    /** 保存上传Firebase注册的Token的action */
    var action: String? = null

    /** 保存用户Token */
    var userToken: String? = null

    companion object {
        val singleInstance: YDFirebaseSDK by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { YDFirebaseSDK() }
    }

    fun  registerNotification(application: Application) {

        FirebaseMessaging.getInstance().isAutoInitEnabled = true
        FirebaseApp.initializeApp(application)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            val channelId = "fcm_firebase_channel"
            val channelName = "Firebase推送"
            val notificationManager = application.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            )
        }

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }

                this.firebaseToken = task.result?.token

                uploadFirebaseToken()
            })
    }

    /**
     * 设置AutoInitEnabled
     *
     * @param enabled 设置enable
     * */
    fun setAutoInitEnabled(enabled: Boolean) {
        FirebaseMessaging.getInstance().isAutoInitEnabled = enabled
    }

    /**
     * 检测是否安装GooglePlay
     * */
    fun makeGooglePlayServicesAvailable(activity: Activity) {
        GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(activity)
    }

    /**
     * 设置推送参数
     *
     * @pram baseURL 上传Token的URL
     * @pram userToken 用户Token
     * @pram action 上传推送Token的action
     * */
    fun setNotification(baseURL: String, userToken: String, action: String) {
        this.baseURL = baseURL
        this.action = action
        this.userToken = userToken

        uploadFirebaseToken()
    }

    fun uploadFirebaseToken() {

        if (this.baseURL != null && this.action != null && this.firebaseToken != null && this.userToken != null) {

            YDFirebaseNetwork.uploadFirebaseToken(this.baseURL!!, this.action!!, this.userToken!!, this.firebaseToken!!)

        }

    }

}