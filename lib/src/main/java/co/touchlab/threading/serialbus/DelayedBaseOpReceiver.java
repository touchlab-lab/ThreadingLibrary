package co.touchlab.threading.serialbus;

import android.content.Context;
import android.content.Intent;
import co.touchlab.threading.receivers.DelayedBroadcastReceiver;

/**
 * Implementation of DelayedBroadcastReceiver to interpret intent results.
 *
 * Created by kgalligan on 5/30/14.
 */
public abstract class DelayedBaseOpReceiver extends DelayedBroadcastReceiver
{
    @Override
    public final void onReceiveDelayed(Context context, Intent intent)
    {
        if(BaseOp.isSuccess(intent))
            onSuccess(context, intent);
        else
            onFailure(context, intent);
    }

    public abstract void onSuccess(Context context, Intent intent);
    public abstract void onFailure(Context context, Intent intent);
}
