package co.touchlab.threading.example;

import android.content.Context;
import co.touchlab.threading.loaders.BaseAsyncLoader;

/**
 * Created by kgalligan on 5/29/14.
 */
public class BasicDataLoader extends BaseAsyncLoader<SomeData>
{
    public BasicDataLoader(Context context)
    {
        super(context);
    }

    @Override
    public SomeData loadInBackground()
    {
        return new SomeData();
    }
}
