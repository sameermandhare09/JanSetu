package cssl.dialstar.representative_util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private boolean isConnected = false;


    @Override
    public void onReceive(final Context context, final Intent intent) {

       boolean isconnected= isNetworkAvailable(context);


       if(isconnected)
       {
           Intent intent1 = new Intent("my.action.string");
           intent1.putExtra("Connected","true");
           intent1.setPackage("cssl.dialstar");
           context.sendBroadcast(intent1);


        /*   Intent intent1 = new Intent();
           intent1.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
           intent1.setAction(ConnectivityManager.CONNECTIVITY_ACTION);
           intent1.putExtra("Foo", "Bar");
           context.sendBroadcast(intent);*/

       }


    }





    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        if(!isConnected){
                            isConnected = true;
                            // login();



                        }
                        return true;
                    }
                }
            }
        }

        isConnected = false;
        return isConnected;
    }


}
