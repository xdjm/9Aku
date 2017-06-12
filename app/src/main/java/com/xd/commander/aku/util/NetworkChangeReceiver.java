package com.xd.commander.aku.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.xd.commander.aku.constants.Constants;

/**
 * Created by Administrator on 2017/4/25.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(isNetworkAvailable(context)){
            Intent networkChange=new Intent(Constants.NETWORK_DETECTOR);
            networkChange.putExtra(Constants.DATA_AVAILABLE,true);
            context.sendBroadcast(networkChange);
        }else{
            Intent networkChange=new Intent(Constants.NETWORK_DETECTOR);
            networkChange.putExtra(Constants.DATA_AVAILABLE,false);
            context.sendBroadcast(networkChange);
        }
    }
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
