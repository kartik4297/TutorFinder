package com.example.kartik.tutorfinder;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

/**
 * Created by kartik on 25-02-2018.
 */

public class ConnectionError {
    Context ctx;
    public ConnectionError(Context ctx) {
        this.ctx = ctx;
    }
    public boolean checkInternetConenction() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec
                =(ConnectivityManager)ctx.getSystemService(ctx.CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() ==
                android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
            Toast.makeText(ctx, " Connected ", Toast.LENGTH_SHORT).show();
            return true;
        }else if (
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() ==
                                android.net.NetworkInfo.State.DISCONNECTED  ) {
            Toast.makeText(ctx, "Offline", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setMessage("Turn of Data or Wifi")
                    .setNegativeButton("Ok", null)
                    .create()
                    .show();
            return false;
        }
        return false;
    }

    public void displayMessage(String toastString) {
        Toast.makeText(ctx, toastString, Toast.LENGTH_LONG).show();
    }
}
