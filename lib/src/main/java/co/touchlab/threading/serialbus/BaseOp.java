package co.touchlab.threading.serialbus;

import android.app.Application;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Base operation for background processes.  Definition is kind of loose, so be prepared to do your own thing.
 *
 * Created by kgalligan on 5/28/14.
 */
public abstract class BaseOp implements Runnable
{
    public static final String SUCCESS = "success";
    public static final String MESSAGE = "message";
    private final Application context;

    public BaseOp(Application context)
    {
        this.context = context;
    }

    public Application getContext()
    {
        return context;
    }

    protected void sendSuccess(String action)
    {
        sendResult(action, true, null);
    }

    protected void sendFailure(String action)
    {
        sendResult(action, false, null);
    }

    protected void sendFailure(String action, String message)
    {
        sendResult(action, false, message);
    }

    protected void sendResult(String action, boolean val)
    {
        sendResult(action, val, null);
    }

    protected void sendResult(String action, boolean val, String message)
    {
        sendResultIntent(buildResultIntent(action,val,message));
    }

    protected boolean sendResultIntent(Intent intent)
    {
        return LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }

    protected Intent buildResultIntent(String action, boolean val)
    {
        return buildResultIntent(action, val, null);
    }

	protected Intent buildResultIntent(String action, boolean val, String message) {
		Intent intent = new Intent(action);
		intent.putExtra(SUCCESS, val);
		if(message != null)
			intent.putExtra(MESSAGE, message);
		return intent;
	}

}
