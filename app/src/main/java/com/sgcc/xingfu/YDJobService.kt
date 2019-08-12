package com.sgcc.xingfu

import android.app.job.JobParameters
import android.app.job.JobService
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class YDJobService : JobService() {

    override fun onStartJob(parameters: JobParameters?): Boolean {

        Log.i("wuyezhiguhun", ":<- YDJobService - onStartJob - parameters ->: " + parameters)

        return false
    }

    override fun onStopJob(parameters: JobParameters?): Boolean {

        Log.i("wuyezhiguhun", ":<- YDJobService - onStopJob - parameters ->: " + parameters)

        return false
    }

}