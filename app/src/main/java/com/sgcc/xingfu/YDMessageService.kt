package com.sgcc.xingfu

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior.setTag



class YDMessageService : FirebaseMessagingService()  {

    override fun onMessageSent(message: String?) {
        super.onMessageSent(message)

        Log.i("wuyezhiguhun", ":<- YDMessageService ->: onMessageSent ->: message ->: " + message)
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()


    }

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        remoteMessage?.toIntent()?.extras.let {

            Log.i("wuyezhiguhun", ":<- YDMeessageService - onMessageReceived ->: Intent ->: extras ->: " + it)

        }


        Log.i("wuyezhiguhun", ":<- YDMeessageService - onMessageReceived ********** ->:")

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("wuyezhiguhun", "YDMessageService - From: ${remoteMessage?.from}")

        Log.i("wuyezhiguhun", ":<- YDMessageService ->: onMessageReceived ->: remoteMessage ->: " + remoteMessage)
        Log.i("wuyezhiguhun", ":<- YDMessageService ->: onMessageReceived ->: remoteMessage from ->: " + remoteMessage?.from)

        // Check if message contains a data payload.
        remoteMessage?.data?.isNotEmpty()?.let {
            Log.i("wuyezhiguhun", "YDMessageService - Message data payload: " + remoteMessage.data)

            Log.i("wuyezhiguhun", ":<- YDMessageService ->: onMessageReceived ->: remoteMessage data ->: " + remoteMessage.data)

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                scheduleJob()
            } else {
                // Handle message within 10 seconds
                handleNow()
            }
        }

        // Check if message contains a notification payload.
        remoteMessage?.notification?.let {

            Log.i("wuyezhiguhun", ":<- YDMessageService ->: onMessageReceived ->: remoteMessage it ->: " + it)

            Log.i("wuyezhiguhun", ":<- YDMessageService ->: onMessageReceived ->: remoteMessage body ->: " + it.body)

            Log.i("wuyezhiguhun", ":<- YDMessageService ->: onMessageReceived ->: remoteMessage body ->: " + remoteMessage)

            Log.i("wuyezhiguhun", "YDMessageService - Message Notification Body: ${it.body}")
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    // [START on_new_token]
    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String?) {
        Log.i("wuyezhiguhun", "YDMessageService - Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token)
    }
    // [END on_new_token]

    /**
     * Schedule async work using WorkManager.
     */
    private fun scheduleJob() {
        Log.i("wuyezhiguhun", " :<- YDMessageService - scheduleJob ->: ")

//        val dispatcher = FirebaseJobDispatcher(GooglePlayDriver(this))
//        val myJob = dispatcher.newJobBuilder()
//            .setService(YDJobService::class.java)
//            .setTag("my-job-tag")
//            .build()
//        dispatcher.schedule(myJob)

        // [START dispatch_job]
//        val work = OneTimeWorkRequest.Builder(MyWorker::class.java).build()
//        WorkManager.getInstance().beginWith(work).enqueue()
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private fun handleNow() {
        Log.i("wuyezhiguhun", "YDMessageService - Short lived task is done.")
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private fun sendRegistrationToServer(token: String?) {
        Log.i("wuyezhiguhun", ":<- YDMessageService - sendRegistrationToServer ->: " + token)
        // TODO: Implement this method to send token to your app server.
    }

}