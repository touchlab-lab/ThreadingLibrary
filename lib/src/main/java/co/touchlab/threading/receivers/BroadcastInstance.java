package co.touchlab.threading.receivers;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

/**
 * Data object for DelayedBroadcastReceiver
 * Created by kgalligan on 5/28/14.
 */
class BroadcastInstance
{
    private Context context;
    private Intent intent;

    protected BroadcastInstance(Context context, Intent intent)
    {
        this.context = context instanceof Application ? context : context.getApplicationContext();
        this.intent = intent;
    }

    protected Context getContext()
    {
        return context;
    }

    protected Intent getIntent()
    {
        return intent;
    }
}
