package com.sgcc.xingfu

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import android.content.Intent
import com.sgcc.ydfirebasesdk.YDFirebaseMessageEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        EventBus.getDefault().register(this)

        GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this)
        FirebaseMessaging.getInstance().isAutoInitEnabled = true


        if (intent != null) {

            Log.i("wuyezhiguhun", ":<- MainActivity ->: onCreate ->: intent ->: hasExtra ->: " + intent.extras.toString())

            Log.i("wuyezhiguhun", ":<- MainActivity ->: onCreate ->: intent ->: hasExtra ->: app_page ->: " + intent.hasExtra("app_page"))

        }
    }

    @Subscribe
    fun handleFirebaseMessageEvent(event: YDFirebaseMessageEvent) {

        Log.i("wuyezhiguhun", ":<- MainActivity ->: handleFirebaseMessageEvent ->: event ->: messageBody ->: " + event.messageBody)

        Log.i("wuyezhiguhun", ":<- MainActivity ->: handleFirebaseMessageEvent ->: event ->: messageData ->: " + event.messageData)

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun onResume() {
        super.onResume()

        if (intent != null) {

            Log.i("wuyezhiguhun", ":<- MainActivity ->: onResume ->: intent ->: hasExtra ->: " + intent.extras.toString())

            Log.i("wuyezhiguhun", ":<- MainActivity ->: onResume ->: intent ->: hasExtra ->: app_page ->: " + intent.hasExtra("app_page"))

            Log.i("wuyezhiguhun", ":<- MainActivity ->: onResume ->: intent ->: hasExtra ->: Bundle ->: " + intent.hasExtra("Bundle"))

            Log.i("wuyezhiguhun", ":<- MainActivity ->: onResume ->: intent ->: hasExtra ->: app_page ->: string ->: " + intent.getStringExtra("app_page"))

            if ("test_page_android" == intent.getStringExtra("app_page")) {
                val login = Intent(this, YDMessageActivity::class.java)
                startActivity(login)
                // Closing dashboard screen
                finish()
            }
        }

//        val function = Functions()
//
//        val userFunctions = UserFunctions()
//        if (userFunctions.isUserLoggedIn(applicationContext)) {
//            val intent_o = intent
//            val fcm_notification = intent_o.getStringExtra("fcm_notification")
//            val user_id = intent_o.getStringExtra("user_id")
//            val date = intent_o.getStringExtra("date")
//            val hal_id = intent_o.getStringExtra("hal_id")
//            val M_view = intent_o.getStringExtra("M_view")
//            val intent = Intent(this, JobList::class.java)
//
//            // THIS RETURNS NULL, user_id = null
//            print("FCM" + user_id!!)
//            startActivity(intent)
//            finish()
//        } else {
//            // user is not logged in show login screen
//            val login = Intent(this, YDMessageActivity::class.java)
//            startActivity(login)
//            // Closing dashboard screen
//            finish()
//        }
    }
}
