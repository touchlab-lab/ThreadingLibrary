package co.touchlab.threading;

import android.os.Looper;

/**
 * If you want to make for real sure you're in/not-in the main thread.
 *
 * Created by kgalligan on 5/28/14.
 */
public class AssertThread
{
    public static boolean isMainThread()
    {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static void inMain()
    {
        if (!isMainThread())
            throw new RuntimeException("Assert failed. Not in main thread");
    }

    public static void notInMain()
    {
        if (isMainThread())
            throw new RuntimeException("Assert failed. In main thread");
    }
}
