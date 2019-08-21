package com.example.mit.exiomstask.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.mit.exiomstask.utils.Constant;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CheckDistanceService extends JobService {


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        Log.e("CheckDistanceService","onStartJob");
//        Constant.scheduleJob(this);

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.e("CheckDistanceService","onStopJob");
        return true;
    }
}
