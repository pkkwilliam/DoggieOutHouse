package doggieouthouse.com.doggieouthouse.UI.functions;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by pkkwilliam on 4/11/18.
 */

public class WifiConnectionCheck {
    Context context;
    public WifiConnectionCheck(Context context){
        this.context = context;
    }
    public boolean checkWifiOnAndConnected() {
        WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        if (wifiMgr.isWifiEnabled()) { // Wi-Fi adapter is ON

            WifiInfo wifiInfo = wifiMgr.getConnectionInfo();

            if( wifiInfo.getNetworkId() == -1 ){
                return false; // Not connected to an access point
            }
            return true; // Connected to an access point
        }
        else {
            return false; // Wi-Fi adapter is OFF
        }
    }
}
