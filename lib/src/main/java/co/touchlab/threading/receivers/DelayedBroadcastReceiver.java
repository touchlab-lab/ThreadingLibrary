package co.touchlab.threading.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Hold notifications when screen isn't active.  Run when we come back to life.
 *
 * Created by kgalligan on 5/28/14.
 */
public abstract class DelayedBroadcastReceiver extends BroadcastReceiver
{
    private boolean active;
    private List<BroadcastInstance> broadcastInstances = new ArrayList<BroadcastInstance>();

    @Override
    public final void onReceive(Context context, Intent intent)
    {
        if(active)
            onReceiveDelayed(context, intent);
        else
        {
            broadcastInstances.add(new BroadcastInstance(context, intent));
        }
    }

    public synchronized void markActive()
    {
        if(active)
            throw new IllegalStateException("Already active");

        active = true;
        for (BroadcastInstance broadcastInstance : broadcastInstances)
        {
            onReceiveDelayed(broadcastInstance.getContext(), broadcastInstance.getIntent());
        }
        broadcastInstances.clear();
    }

    public synchronized void markInactive()
    {
        if(!active)
            throw new IllegalStateException("Already inactive");

        active = false;
    }

    public abstract void onReceiveDelayed(Context context, Intent intent);

    public abstract IntentFilter createFilter();
}