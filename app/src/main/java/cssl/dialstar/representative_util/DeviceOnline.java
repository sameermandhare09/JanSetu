package cssl.dialstar.representative_util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class DeviceOnline {

    public static boolean isDeviceOnline(Context context) {

        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        boolean isOnline = (networkInfo != null && networkInfo.isConnected());
        if(!isOnline)
            return false;
            //Toast.makeText(context, " No internet Connection ", Toast.LENGTH_SHORT).show();


        return isOnline;
    }
}
