package com.example.kartik.tutorfinder.LoginRegister;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;

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
          //  Toast.makeText(ctx, " Connected ", Toast.LENGTH_SHORT).show();
            return true;
        }else if (
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() ==
                                android.net.NetworkInfo.State.DISCONNECTED  ) {
            Toast.makeText(ctx, "No Internet", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setMessage("Connect to internet")
                    .setNegativeButton("Ok", null)
                    .create()
                    .show();
            return false;
        }
        return false;
    }
//display method is used to show the toast message of LONG length
    public void displayMessage(String toastString) {
        Toast.makeText(this.ctx, toastString, Toast.LENGTH_LONG).show();
    }


    //This method is being used in onErrorResponse(VolleyError error) method to handle the error

    public void volleyErrorHandling(VolleyError error)
    {
        String json = null;
        NetworkResponse nresponse = error.networkResponse;
        //    int statusCode = nresponse.statusCode;
        //   System.out.println("ErrorCode.........................." + statusCode);
        if (nresponse != null && nresponse.data != null) {
            json = new String(nresponse.data);
            if (json != null)
                displayMessage(json);
            switch (nresponse.statusCode) {
                case 404:
                    displayMessage( "incorrect URL requested.\nERROR_CODE=404");
                    break;
                //more error can be listed
                default:
                    displayMessage("invalid error");
            }
        }
        if (nresponse == null || nresponse.data == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.ctx);
            builder.setMessage("Server not found! Retry after sometimes.")
                    .setNegativeButton("Ok", null)
                    .create()
                    .show();
        }
    }

}
