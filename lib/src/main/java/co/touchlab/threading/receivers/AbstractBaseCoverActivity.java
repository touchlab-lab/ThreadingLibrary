package co.touchlab.threading.receivers;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Base activity class to manage delay broadcast receivers
 *
 * Created by kgalligan on 5/29/14.
 */
public class AbstractBaseCoverActivity extends Activity
{
    private List<DelayedBroadcastReceiver> delayedBroadcastReceivers = new ArrayList<DelayedBroadcastReceiver>();

    public void addDelayedBroadcastReceiver(DelayedBroadcastReceiver receiver)
    {
        delayedBroadcastReceivers.add(receiver);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, receiver.createFilter());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        for (DelayedBroadcastReceiver delayedBroadcastReceiver : delayedBroadcastReceivers)
        {
            delayedBroadcastReceiver.markActive();
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        for (DelayedBroadcastReceiver delayedBroadcastReceiver : delayedBroadcastReceivers)
        {
            delayedBroadcastReceiver.markInactive();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        for (DelayedBroadcastReceiver delayedBroadcastReceiver : delayedBroadcastReceivers)
        {
            broadcastManager.unregisterReceiver(delayedBroadcastReceiver);
        }
    }
}
