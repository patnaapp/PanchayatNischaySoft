package app.bih.in.nic.nischayyojana.entity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyScheduledReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent recievedIntent) {
        // TODO Auto-generated method stub
        Toast.makeText(context, "Session Timeout", Toast.LENGTH_LONG).show();
        Log.v("MyScheduledReceiver", "Intent Fired");

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("truiton.ACTION_FINISH");
        context.sendBroadcast(broadcastIntent);
    }

}