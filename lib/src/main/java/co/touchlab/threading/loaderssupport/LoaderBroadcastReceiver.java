package co.touchlab.threading.loaderssupport;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

/**
 * Created by kgalligan on 5/28/14.
 */
class LoaderBroadcastReceiver extends BroadcastReceiver
{
    private final Handler handler;
    private final LoaderBroadcastReceiver.ContentChangedRunnable contentChangedRunnable;
    private BaseAsyncLoader loader;

    protected LoaderBroadcastReceiver(BaseAsyncLoader loader)
    {
        handler = new Handler();
        contentChangedRunnable = new ContentChangedRunnable();
        this.loader = loader;
    }

    class ContentChangedRunnable implements Runnable
    {
        @Override
        public void run()
        {
            loader.onContentChanged();
        }
    }


    @Override
    public void onReceive(Context context, Intent intent)
    {
        handler.removeCallbacks(contentChangedRunnable);
        handler.postDelayed(contentChangedRunnable, 300);
    }
}
